package com.moldavets.ecom_store.order.model.order.model;

import lombok.Builder;

import java.util.List;


@Builder
public record DetailCartRequest(List<DetailCartItemRequest> items) {
}
