package com.moldavets.ecom_store.product.model;

import com.moldavets.ecom_store.product.vo.ProductSize;
import com.moldavets.ecom_store.product.vo.UserPublicId;
import lombok.Builder;

import java.util.List;

@Builder
public record FilterQuery(UserPublicId id, List<ProductSize> sizes) {
}
