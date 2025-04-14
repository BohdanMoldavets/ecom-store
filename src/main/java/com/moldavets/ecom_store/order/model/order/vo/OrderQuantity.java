package com.moldavets.ecom_store.order.model.order.vo;

public record OrderQuantity(long value) {

    public OrderQuantity {
        if(value <= 0) {
            throw new IllegalArgumentException("Order quantity must be positive");
        }
    }

}
