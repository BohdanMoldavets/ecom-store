package com.moldavets.ecom_store.order.model.order.model;

import com.moldavets.ecom_store.product.vo.PublicId;
import lombok.Builder;


@Builder
public record DetailCartItemRequest(PublicId productId, long quantity) {
}
