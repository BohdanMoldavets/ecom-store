package com.moldavets.ecom_store.order.model.order.model;

import com.moldavets.ecom_store.order.model.order.vo.OrderQuantity;
import com.moldavets.ecom_store.order.model.order.vo.ProductPublicId;
import lombok.Builder;

@Builder
public record OrderProductQuantity(OrderQuantity quantity,
                                   ProductPublicId publicId) {
}
