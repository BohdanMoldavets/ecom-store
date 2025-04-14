package com.moldavets.ecom_store.product.repository;

import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository {

    Product save(Product productToBeCreated);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findByPublicId(PublicId publicId);

    Page<Product> findByCategoryExcludingOne(Pageable pageable, PublicId categoryPublicId, PublicId productPublicId);

    Page<Product> findAllFeaturedProduct(Pageable pageable);

    int delete(PublicId publicId);
}


