package com.moldavets.ecom_store.product.vo;

public record ProductDescription(String value) {

    public ProductDescription {
        if(value == null || value.length() <= 10) {
            throw new RuntimeException();
            //TODO EXCEPTION
        }
    }
}
