package com.moldavets.ecom_store.order.infrastructure.secondary.entity;

import com.moldavets.ecom_store.order.model.user.model.Authority;
import com.moldavets.ecom_store.order.model.user.vo.AuthorityName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "authority")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorityEntity implements Serializable {

    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private String name;

    public static Set<AuthorityEntity> from(Set<Authority> authorities) {
        return authorities.stream()
                .map(auth -> new AuthorityEntity(auth.getName().name()))
                .collect(Collectors.toSet());
    }

    public static Set<Authority> to(Set<AuthorityEntity> authorities) {
        return authorities.stream()
                .map(auth -> new Authority(new AuthorityName(auth.getName())))
                .collect(Collectors.toSet());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        AuthorityEntity that = (AuthorityEntity) object;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
