package com.moldavets.ecom_store.order.model.user.vo;

import lombok.Builder;

import java.util.Objects;

@Builder
public record UserAddressToUpdate(UserPublicId publicId, UserAddress userAddress) {

    public UserAddressToUpdate {
        Objects.requireNonNull(publicId);
        Objects.requireNonNull(userAddress);
    }
}
