package com.moldavets.ecom_store.product.vo;

public record ProductName(String value) {

    public ProductName {
        if(value == null || value.length() <= 10 || value.length() > 256) {
            throw new RuntimeException();
            //TODO EXCEPTION
        }
    }
}
