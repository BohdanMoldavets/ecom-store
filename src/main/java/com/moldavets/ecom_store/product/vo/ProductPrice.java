package com.moldavets.ecom_store.product.vo;

public record ProductPrice(double value) {

    public ProductPrice {
        if(value < 0.1) {
            //TODO THROW EXCEPTION
        }
    }
}
