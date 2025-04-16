package com.moldavets.ecom_store.order.infrastructure.primary.model;

import com.moldavets.ecom_store.order.model.order.model.DetailCartItemRequest;
import com.moldavets.ecom_store.product.vo.PublicId;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RestCartItemRequest(UUID publicId,
                                  long quantity) {

    public static DetailCartItemRequest to(RestCartItemRequest item) {
        return DetailCartItemRequest.builder()
                .productId(new PublicId(item.publicId))
                .quantity(item.quantity)
                .build();
    }

    public static List<DetailCartItemRequest> to(List<RestCartItemRequest> items) {
        return items.stream()
                .map(RestCartItemRequest::to)
                .toList();
    }

    public static RestCartItemRequest from(DetailCartItemRequest item) {
        return RestCartItemRequest.builder()
                .publicId(item.productId().id())
                .quantity(item.quantity())
                .build();
    }

    public static List<RestCartItemRequest> from(List<DetailCartItemRequest> items) {
        return items.stream()
                .map(RestCartItemRequest::from)
                .toList();
    }

}
