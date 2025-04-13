package com.moldavets.ecom_store.product.vo;

public record ProductBrand(String value) {
    public ProductBrand {
        if(value == null || value.length() <= 2) {
            throw new RuntimeException();
            //TODO EXCEPTION
        }
    }
}
