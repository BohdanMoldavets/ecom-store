package com.moldavets.ecom_store.product.infrastructure.primary.model;

import com.moldavets.ecom_store.product.model.Category;
import com.moldavets.ecom_store.product.vo.CategoryName;
import com.moldavets.ecom_store.product.vo.PublicId;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RestCategory(UUID publicId,
                           String name) {

    public RestCategory {
        if(name == null) {
            throw new NullPointerException("name is null");
            //TODO EXCEPTION
        }
    }

    public static Category to(RestCategory category) {
        Category.CategoryBuilder builder = Category.builder();

        if(category.name != null) {
            builder.categoryName(new CategoryName(category.name));
        }

        if(category.publicId != null) {
            builder.publicId(new PublicId(category.publicId));
        }

        return builder.build();
    }

    public static RestCategory from(Category category) {
        RestCategoryBuilder builder = RestCategory.builder();

        if(category.getCategoryName() != null) {
            builder.name(category.getCategoryName().value());
        }

        return builder
                .publicId(category.getPublicId().id())
                .build();
    }
}
