package com.moldavets.ecom_store.order.infrastructure.primary.model;

import com.moldavets.ecom_store.order.model.order.vo.StripeSessionId;
import lombok.Builder;

@Builder
public record RestStripeSession(String id) {
    public static RestStripeSession from(StripeSessionId stripeSessionId) {
        return RestStripeSession.builder()
                .id(stripeSessionId.value())
                .build();
    }
}
