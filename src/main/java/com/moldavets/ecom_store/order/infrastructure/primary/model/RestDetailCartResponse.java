package com.moldavets.ecom_store.order.infrastructure.primary.model;

import com.moldavets.ecom_store.order.model.order.model.DetailCartResponse;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record RestDetailCartResponse(List<RestProductCart> products) {

    public static RestDetailCartResponse from(DetailCartResponse detailCartResponse) {
        return RestDetailCartResponse.builder()
                .products(detailCartResponse.getProducts().stream()
                        .map(RestProductCart::from)
                        .toList())
                        .build();
    }

}
