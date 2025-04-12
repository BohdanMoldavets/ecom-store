package com.moldavets.ecom_store.product.model;

import lombok.Builder;

@Builder
public record Picture(byte[] file,
                      String mimeType) {

    public Picture {
        if(file == null || mimeType == null) {
            //TODO EXCEPTION
        }
    }

}
