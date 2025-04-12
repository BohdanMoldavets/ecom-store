package com.moldavets.ecom_store.product.infrastructure.secondary.repository;

import com.moldavets.ecom_store.product.infrastructure.secondary.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductPictureRepository extends JpaRepository<PictureEntity, Long> {
}
