package com.moldavets.ecom_store.product.repository;

import com.moldavets.ecom_store.product.model.Category;
import com.moldavets.ecom_store.product.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepository {

    Page<Category> findAll(Pageable pageable);
    Integer deleteById(UserPublicId publicId);
    Category save(Category categoryToBeCreated);
}
