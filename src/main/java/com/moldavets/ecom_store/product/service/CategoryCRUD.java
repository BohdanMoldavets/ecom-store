package com.moldavets.ecom_store.product.service;

import com.moldavets.ecom_store.product.model.Category;
import com.moldavets.ecom_store.product.repository.CategoryRepository;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CategoryCRUD {

    private final CategoryRepository categoryRepository;

    public CategoryCRUD(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category category) {
        category.initDefaultFields();
        return categoryRepository.save(category);
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public PublicId deleteById(PublicId categoryId) {
        int numOfDeletedRows = categoryRepository.deleteById(categoryId);
        if(numOfDeletedRows != 1) {
            //TODO EXCEPTION ENTITY NOT FOUND;
        }
        return categoryId;
    }

}
