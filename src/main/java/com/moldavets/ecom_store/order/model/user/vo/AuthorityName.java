package com.moldavets.ecom_store.order.model.user.vo;

import java.util.Objects;

public record AuthorityName(String name) {

    public AuthorityName {
        Objects.requireNonNull(name);
    }

}
