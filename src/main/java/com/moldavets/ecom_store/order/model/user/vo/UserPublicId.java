package com.moldavets.ecom_store.order.model.user.vo;

import java.util.Objects;
import java.util.UUID;

public record UserPublicId(UUID value) {

    public UserPublicId {
        Objects.requireNonNull(value);
    }

}
