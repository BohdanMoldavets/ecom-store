package com.moldavets.ecom_store.product.vo;

import java.util.UUID;

public record PublicId(UUID id) {
    public PublicId {
        if(id == null) {
            //TODO THROW EXCEPTION
        }
    }
}
