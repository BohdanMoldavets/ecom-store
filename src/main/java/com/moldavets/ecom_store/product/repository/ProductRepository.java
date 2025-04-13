package com.moldavets.ecom_store.product.repository;

import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {
    Product save(Product productToBeCreated);
    Page<Product> findAll(Pageable pageable);
    int delete(PublicId publicId);

}
