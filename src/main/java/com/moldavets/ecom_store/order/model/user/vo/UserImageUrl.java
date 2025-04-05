package com.moldavets.ecom_store.order.model.user.vo;

import java.util.Objects;

public record UserImageUrl(String value) {

    public UserImageUrl {
        Objects.requireNonNull(value);
        if(value.length() > 255) {
            //TODO EXCEPTION
        }
    }
}
