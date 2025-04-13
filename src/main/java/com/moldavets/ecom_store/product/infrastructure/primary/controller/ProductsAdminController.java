package com.moldavets.ecom_store.product.infrastructure.primary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moldavets.ecom_store.product.infrastructure.primary.exception.EntityNotFoundException;
import com.moldavets.ecom_store.product.infrastructure.primary.exception.MultipartPictureException;
import com.moldavets.ecom_store.product.infrastructure.primary.model.RestPicture;
import com.moldavets.ecom_store.product.infrastructure.primary.model.RestProduct;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.service.ProductApplicationService;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductsAdminController {

    protected static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final ProductApplicationService productApplicationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ProductsAdminController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @GetMapping
    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    public ResponseEntity<Page<RestProduct>> getAll(Pageable pageable) {
        Page<Product> products = productApplicationService.findAllProducts(pageable);

        Page<RestProduct> restProduct = new PageImpl<>(
            products.getContent().stream().map(RestProduct::from).collect(Collectors.toList()),
            pageable,
            products.getTotalElements()
        );
        return new ResponseEntity<>(restProduct, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestProduct> save(MultipartHttpServletRequest request,
                                            @RequestPart("dto") String productRaw) throws JsonProcessingException {

        List<RestPicture> pictures = request.getFileMap()
                .values()
                .stream()
                .map(mapMultipartFileToRestPicture())
                .toList();

        RestProduct restProduct = objectMapper.readValue(productRaw, RestProduct.class);
        restProduct.addPictureAttachment(pictures);

        Product newProduct = RestProduct.to(restProduct);
        Product storedProduct = productApplicationService.createProduct(newProduct);
        return new ResponseEntity(RestProduct.from(storedProduct), HttpStatus.CREATED);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('" + ROLE_ADMIN + "')")
    public ResponseEntity<UUID> deleteProductById(@RequestParam("publicId") UUID id) {
        try {
            PublicId publicId = productApplicationService.deleteProduct(new PublicId(id));
            return new ResponseEntity<>(publicId.id(), HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.of(problemDetail).build();
        }
    }

    private Function<MultipartFile, RestPicture> mapMultipartFileToRestPicture() {
        return multipartFile -> {
            try {
                return new RestPicture(multipartFile.getBytes(), multipartFile.getContentType());
            } catch (IOException e) {
                throw new MultipartPictureException(
                        String.format("Failed to parse multipart file: %s", multipartFile.getOriginalFilename())
                );
            }
        };
    }

}
