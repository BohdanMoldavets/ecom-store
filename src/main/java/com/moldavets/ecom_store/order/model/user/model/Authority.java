package com.moldavets.ecom_store.order.model.user.model;

import com.moldavets.ecom_store.order.model.user.vo.AuthorityName;
import lombok.Builder;

import java.util.Objects;

@Builder
public class Authority {

    private AuthorityName name;

    public Authority(AuthorityName authorityName) {
        Objects.requireNonNull(authorityName);
        this.name = authorityName;
    }

    public AuthorityName getName() {
        return name;
    }
}
