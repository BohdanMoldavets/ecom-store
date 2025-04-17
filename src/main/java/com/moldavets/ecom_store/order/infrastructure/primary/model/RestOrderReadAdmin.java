package com.moldavets.ecom_store.order.infrastructure.primary.model;

import com.moldavets.ecom_store.order.model.order.model.Order;
import com.moldavets.ecom_store.order.model.order.vo.OrderStatus;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RestOrderReadAdmin(UUID publicId,
                                 OrderStatus status,
                                 List<RestOrderedItemRead> orderedItems,
                                 String address,
                                 String email) {

    public static RestOrderReadAdmin from (Order order) {
        StringBuilder address = new StringBuilder();
        if(order.getUser().getUserAddress() != null) {
            address.append(order.getUser().getUserAddress().street());
            address.append(", ");
            address.append(order.getUser().getUserAddress().city());
            address.append(", ");
            address.append(order.getUser().getUserAddress().zipCode());
            address.append(", ");
            address.append(order.getUser().getUserAddress().country());
        }
        return RestOrderReadAdmin.builder()
                .publicId(order.getPublicId().id())
                .status(order.getStatus())
                .orderedItems(RestOrderedItemRead.from(order.getOrderedProducts()))
                .address(address.toString())
                .email(order.getUser().getEmail().value())
                .build();
    }

}