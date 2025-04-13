package com.moldavets.ecom_store.product.infrastructure.primary.exception;

public class EntityNotFoundException extends RuntimeException {
    //TODO MOVE TO COMMON PACKAGE
    public EntityNotFoundException(String message) {
        super(message);
    }
}
