package com.moldavets.ecom_store.product.infrastructure.primary.controller;

import com.moldavets.ecom_store.product.infrastructure.primary.exception.EntityNotFoundException;
import com.moldavets.ecom_store.product.infrastructure.primary.model.RestCategory;
import com.moldavets.ecom_store.product.model.Category;
import com.moldavets.ecom_store.product.service.ProductApplicationService;
import com.moldavets.ecom_store.product.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.moldavets.ecom_store.product.infrastructure.primary.controller.ProductsAdminController.ROLE_ADMIN;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ProductApplicationService productApplicationService;

    public CategoryController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @GetMapping
    public ResponseEntity<Page<RestCategory>> getAll(Pageable pageable) {
        Page<Category> categories = productApplicationService.findAllCategories(pageable);
        Page<RestCategory> restCategories =  new PageImpl<>(
                categories.stream().map(RestCategory::from).toList(),
                pageable,
                categories.getTotalElements()
        );
        return new ResponseEntity<>(restCategories, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    public ResponseEntity<RestCategory> save(@RequestBody RestCategory restCategory) {
        Category category =  RestCategory.to(restCategory);
        Category storedCategory = productApplicationService.createCategory(category);
        return new ResponseEntity<>(RestCategory.from(storedCategory), HttpStatus.CREATED);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    public ResponseEntity<UUID> deleteCategoryById(@RequestParam("publicId") UUID id) {
        try {
            UserPublicId deletedId = productApplicationService.deleteCategory(new UserPublicId(id));
            return new ResponseEntity<>(deletedId.id(), HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.of(problemDetail).build();
        }
    }

}
