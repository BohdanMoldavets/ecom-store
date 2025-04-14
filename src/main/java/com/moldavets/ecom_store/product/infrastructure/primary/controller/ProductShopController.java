package com.moldavets.ecom_store.product.infrastructure.primary.controller;

import com.moldavets.ecom_store.product.infrastructure.primary.exception.EntityNotFoundException;
import com.moldavets.ecom_store.product.infrastructure.primary.model.RestProduct;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.service.ProductApplicationService;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products-shop")
public class ProductShopController {

    private final ProductApplicationService productApplicationService;

    public ProductShopController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @GetMapping("/featured")
    public ResponseEntity<Page<RestProduct>> getAllFeatured(Pageable pageable) {
        Page<Product> products = productApplicationService.getFeaturedProducts(pageable);

        PageImpl<RestProduct> restProducts = new PageImpl<>(
                products.getContent().stream().map(RestProduct::from).toList(),
                pageable,
                products.getTotalElements()
        );

        return new ResponseEntity<>(restProducts, HttpStatus.OK);
    }

    @GetMapping("/find-one")
    public ResponseEntity<RestProduct> getProductByPublicId(@RequestParam("publicId") UUID publicId) {
        Optional<Product> product =
                productApplicationService.findProductById(new PublicId(publicId));

        return product.map(tempProduct -> new ResponseEntity<>(RestProduct.from(tempProduct), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/related")
    public ResponseEntity<Page<RestProduct>> findRelatedProducts(Pageable pageable,
                                                                 @RequestParam("publicId") UUID publicId) {
        PageImpl<RestProduct> restProducts = null;
        try {
            Page<Product> relatedProducts = productApplicationService.findRelatedProducts(pageable, new PublicId(publicId));
            restProducts = new PageImpl<>(
                    relatedProducts.getContent().stream().map(RestProduct::from).toList(),
                    pageable,
                    relatedProducts.getTotalElements()
            );
            return new ResponseEntity<>(restProducts, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
