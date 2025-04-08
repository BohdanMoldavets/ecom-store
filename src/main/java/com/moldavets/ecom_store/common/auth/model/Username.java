package com.moldavets.ecom_store.common.auth.model;

import java.util.Optional;

public record Username(String username) {

    public Username(String username) {
        if (username == null || username.isEmpty() || username.length() > 100) {
            //TODO EXCEPTION
        }
        this.username = username;
    }

    public String get() {
        return username;
    }

    public static Optional<Username> of(String username) {
        return Optional.ofNullable(username)
                .filter(s -> !s.isEmpty())
                .map(Username::new);
    }
}
