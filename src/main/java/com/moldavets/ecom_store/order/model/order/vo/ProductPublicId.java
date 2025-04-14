package com.moldavets.ecom_store.order.model.order.vo;

import java.util.Objects;
import java.util.UUID;

public record ProductPublicId(UUID value){

    public ProductPublicId {
        Objects.requireNonNull(value);
    }
}
