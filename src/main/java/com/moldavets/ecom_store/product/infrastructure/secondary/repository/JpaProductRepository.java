package com.moldavets.ecom_store.product.infrastructure.secondary.repository;

import com.moldavets.ecom_store.product.infrastructure.secondary.entity.ProductEntity;
import com.moldavets.ecom_store.product.vo.ProductSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

    int deleteByPublicId(UUID publicID);

    Optional<ProductEntity> findByPublicId(UUID publicID);

    Page<ProductEntity> findAllByFeaturedTrue(Pageable pageable);

    Page<ProductEntity> findByCategoryPublicIdAndPublicIdNot(Pageable pageable, UUID categoryPublicId, UUID excludedProductPublicId);


    @Query("SELECT p FROM ProductEntity p WHERE (:sizes IS NULL OR p.size IN (:sizes)) AND p.category.publicId = :categoryPublicId ")
    Page<ProductEntity> findByCategoryPublicIdAndSizesIn(Pageable pageable, UUID categoryPublicId, List<ProductSize> sizes);
}
