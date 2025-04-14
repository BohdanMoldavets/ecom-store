package com.moldavets.ecom_store.order.model.order.vo;

public record OrderPrice(double value) {

    public OrderPrice {
        if(value <= 0) {
            throw new IllegalArgumentException("OrderPrice value must be greater than 0");
        }
    }
}
