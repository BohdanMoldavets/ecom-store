package com.moldavets.ecom_store.product.vo;

public record CategoryName(String value) {

    public CategoryName {
        if(value == null || value.length() <= 2) {
            throw new RuntimeException();
            //TODO EXCEPTION
        }
    }
}
