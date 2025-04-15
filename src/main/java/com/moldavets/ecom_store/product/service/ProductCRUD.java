package com.moldavets.ecom_store.product.service;

import com.moldavets.ecom_store.product.infrastructure.primary.exception.EntityNotFoundException;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.repository.ProductRepository;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class ProductCRUD {

    private final ProductRepository productRepository;

    public ProductCRUD(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        product.initDefaultFields();
        return productRepository.save(product);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public PublicId deleteById(PublicId productId) {
        int numOfDeletedRows = productRepository.delete(productId);
        if(numOfDeletedRows != 1) {
            throw new EntityNotFoundException(
                    String.format("The product with id %s was not found", productId)
            );
        }
        return productId;
    }

    public Optional<Product> findByPublicId(PublicId productId) {
        return productRepository.findByPublicId(productId);
    }

    public List<Product> findAllByPublicIdIn(List<PublicId> publicIds) {
        return productRepository.findByPublicIds(publicIds);
    }
}
