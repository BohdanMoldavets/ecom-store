package com.moldavets.ecom_store.order.infrastructure.primary.model;

import com.moldavets.ecom_store.order.model.order.model.Order;
import com.moldavets.ecom_store.order.model.order.vo.OrderStatus;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RestOrderRead(UUID publicId,
                            OrderStatus orderStatus,
                            List<RestOrderedItemRead> orderedItems) {

    public static RestOrderRead from(Order order) {
        return RestOrderRead.builder()
                .publicId(order.getPublicId().id())
                .orderStatus(order.getStatus())
                .orderedItems(RestOrderedItemRead.from(order.getOrderedProducts()))
                .build();
    }

}
