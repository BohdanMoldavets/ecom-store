package com.moldavets.ecom_store.order.model.user.vo;

import lombok.Builder;

import java.util.Objects;

@Builder
public record UserAddress(String street, String city, String zipCode, String country) {

    public UserAddress {
        Objects.requireNonNull(street);
        Objects.requireNonNull(city);
        Objects.requireNonNull(zipCode);
        Objects.requireNonNull(country);
    }
}
