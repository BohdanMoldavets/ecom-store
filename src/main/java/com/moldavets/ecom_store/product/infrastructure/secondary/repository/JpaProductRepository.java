package com.moldavets.ecom_store.product.infrastructure.secondary.repository;

import com.moldavets.ecom_store.product.infrastructure.secondary.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

    int deleteByPublicId(UUID publicID);

}
