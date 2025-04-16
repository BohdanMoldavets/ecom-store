package com.moldavets.ecom_store.common.excpetion;

public class CartPaymentException extends RuntimeException {
    public CartPaymentException(String message) {
        super(message);
    }
}
