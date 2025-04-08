package com.moldavets.ecom_store.order.model.user.vo;

import java.util.Objects;

public record UserEmail(String value) {

    public UserEmail {
        Objects.requireNonNull(value);
        if(value.length() > 255) {
            //TODO EXCEPTION
        }
    }
}
