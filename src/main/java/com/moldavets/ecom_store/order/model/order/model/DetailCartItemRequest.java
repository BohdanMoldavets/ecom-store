package com.moldavets.ecom_store.order.model.order.model;

import com.moldavets.ecom_store.product.vo.UserPublicId;
import lombok.Builder;


@Builder
public record DetailCartItemRequest(UserPublicId productId, long quantity) {
}
