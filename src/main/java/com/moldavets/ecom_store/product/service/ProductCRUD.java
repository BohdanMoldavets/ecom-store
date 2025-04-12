package com.moldavets.ecom_store.product.service;

import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.repository.ProductRepository;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
            //TODO THROW EXCEPTION
        }
        return productId;
    }

}
