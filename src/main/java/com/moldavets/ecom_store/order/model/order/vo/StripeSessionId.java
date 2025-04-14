package com.moldavets.ecom_store.order.model.order.vo;

import java.util.Objects;

public record StripeSessionId(String value) {

    public StripeSessionId {
        Objects.requireNonNull(value);
    }
}
