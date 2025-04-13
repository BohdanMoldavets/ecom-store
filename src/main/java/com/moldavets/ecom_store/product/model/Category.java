package com.moldavets.ecom_store.product.model;

import com.moldavets.ecom_store.product.vo.CategoryName;
import com.moldavets.ecom_store.product.vo.PublicId;
import lombok.Builder;

import java.util.UUID;

@Builder
public class Category {
    private final CategoryName categoryName;

    private Long dbId;

    private PublicId publicId;

    public Category(CategoryName categoryName, Long dbId, PublicId publicId) {
        assertRequiredFields(categoryName);
        this.categoryName = categoryName;
        this.dbId = dbId;
        this.publicId = publicId;
    }

    public void initDefaultFields() {
        this.publicId = new PublicId(UUID.randomUUID());
    }

    private void assertRequiredFields(CategoryName categoryName) {
        if(categoryName == null) {
            //TODO EXCEPTION
        }
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public Long getDbId() {
        return dbId;
    }

    public PublicId getPublicId() {
        return publicId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public void setPublicId(PublicId publicId) {
        this.publicId = publicId;
    }
}
