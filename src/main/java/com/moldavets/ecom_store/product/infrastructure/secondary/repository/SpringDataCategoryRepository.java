package com.moldavets.ecom_store.product.infrastructure.secondary.repository;

import com.moldavets.ecom_store.product.infrastructure.secondary.entity.CategoryEntity;
import com.moldavets.ecom_store.product.model.Category;
import com.moldavets.ecom_store.product.repository.CategoryRepository;
import com.moldavets.ecom_store.product.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class SpringDataCategoryRepository implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;

    public SpringDataCategoryRepository(JpaCategoryRepository jpaCategoryRepository) {
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return jpaCategoryRepository.findAll(pageable).map(CategoryEntity::to);
    }

    @Override
    public Integer deleteById(UserPublicId publicId) {
        return jpaCategoryRepository.deleteByPublicId(publicId.id());
    }

    @Override
    public Category save(Category categoryToBeCreated) {
        CategoryEntity categoryToSave = CategoryEntity.from(categoryToBeCreated);
        CategoryEntity storedCategory = jpaCategoryRepository.save(categoryToSave);
        return CategoryEntity.to(storedCategory);
    }
}
