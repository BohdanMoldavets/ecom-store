package com.moldavets.ecom_store.order.infrastructure.primary.model;

import com.moldavets.ecom_store.order.model.user.model.Authority;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record RestAuthority(String name) {

    public static Set<String> fromSet(Set<Authority> authorities) {
        return authorities.stream()
                .map(auth -> auth.getName().name())
                .collect(Collectors.toSet());
    }
}
