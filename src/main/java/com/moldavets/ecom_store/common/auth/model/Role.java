package com.moldavets.ecom_store.common.auth.model;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Role {
    ADMIN,USER,ANONYMOUS,UNKNOWN;

    private static final String PREFIX = "ROLE_";
    private static final Map<String, Role> ROLES = buildRoles();

    private static Map<String, Role> buildRoles() {
        return Stream.of(values())
                .collect(Collectors.toMap(Role::key, Function.identity()));
    }

    public String key() {
        return PREFIX + name();
    }

    public static Role from(String role) {
        if(role == null || role.isEmpty()) {
            //TODO EXCEPTION
        }
        return ROLES.getOrDefault(role, UNKNOWN);
    }
}
