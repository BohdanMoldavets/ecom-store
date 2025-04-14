package com.moldavets.ecom_store.product.model;

import com.moldavets.ecom_store.product.vo.ProductSize;
import com.moldavets.ecom_store.product.vo.PublicId;
import lombok.Builder;

import java.util.List;

@Builder
public record FilterQuery(PublicId id, List<ProductSize> sizes) {
}
