package com.moldavets.ecom_store.order.model.user.vo;

import java.util.Objects;

public record UserFirstName(String value) {

    public UserFirstName {
        Objects.requireNonNull(value);
        if(value.length() > 255) {
            //TODO EXCEPTION
        }
    }
}
