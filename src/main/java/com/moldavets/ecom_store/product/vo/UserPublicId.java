package com.moldavets.ecom_store.product.vo;

import java.util.UUID;

public record UserPublicId(UUID id) {
    public UserPublicId {
        if(id == null) {
            //TODO THROW EXCEPTION
        }
    }
}
