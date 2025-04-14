package com.moldavets.ecom_store.product.service;

import com.moldavets.ecom_store.product.infrastructure.primary.exception.EntityNotFoundException;
import com.moldavets.ecom_store.product.model.FilterQuery;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.repository.ProductRepository;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class ProductShop {

    private final ProductRepository productRepository;

    public ProductShop(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getFeaturedProducts(Pageable pageable) {
        return productRepository.findAllFeaturedProduct(pageable);
    }

    public Page<Product> findRelated(Pageable pageable, PublicId productPublicId) {

        Product product = productRepository.findByPublicId(productPublicId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s not found", productPublicId)));

        return productRepository.findByCategoryExcludingOne(
                pageable,
                product.getCategory().getPublicId(),
                productPublicId
        );
    }

    public Page<Product> filter(Pageable pageable, FilterQuery filterQuery) {
        return productRepository.findByCategoryAndSize(pageable, filterQuery);
    }
}
