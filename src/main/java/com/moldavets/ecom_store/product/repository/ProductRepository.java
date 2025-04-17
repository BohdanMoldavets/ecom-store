package com.moldavets.ecom_store.product.repository;

import com.moldavets.ecom_store.order.model.order.vo.ProductPublicId;
import com.moldavets.ecom_store.product.model.FilterQuery;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product productToBeCreated);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findByPublicId(UserPublicId publicId);

    Page<Product> findByCategoryExcludingOne(Pageable pageable, UserPublicId categoryPublicId, UserPublicId productPublicId);

    Page<Product> findAllFeaturedProduct(Pageable pageable);

    Page<Product> findByCategoryAndSize(Pageable pageable, FilterQuery filterQuery);

    List<Product> findByPublicIds(List<UserPublicId> publicIds);

    int delete(UserPublicId publicId);

    void updateQuantity(ProductPublicId productPublicId, long quantity);
}


