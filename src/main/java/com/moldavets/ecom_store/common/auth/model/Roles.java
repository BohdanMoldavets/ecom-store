package com.moldavets.ecom_store.common.auth.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public record Roles(Set<Role> roles) {

    public static final Roles EMPTY = new Roles(null);

    public Roles(Set<Role> roles) {
        this.roles = Collections.unmodifiableSet(roles);
    }

    public boolean hasRole() {
        return !roles.isEmpty();
    }

    public boolean hasRole(Role role) {
        if(role == null) {
            // TODO EXCEPTION
        }
        return roles.contains(role);
    }

    public Stream<Role> stream() {
        return this.get().stream();
    }

    public Set<Role> get() {
        return roles;
    }

}
