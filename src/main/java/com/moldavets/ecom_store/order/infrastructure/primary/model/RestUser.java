package com.moldavets.ecom_store.order.infrastructure.primary.model;

import com.moldavets.ecom_store.order.model.user.model.User;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RestUser(UUID publicId,
                       String firstName,
                       String lastName,
                       String email,
                       String imageUrl,
                       Set<String> authorities) {

    public static RestUser from(User user) {

        RestUserBuilder builder = RestUser.builder();

        if(user.getImageUrl() != null) {
            builder.imageUrl(user.getImageUrl().value());
        }

        return builder
               .publicId(user.getPublicId().value())
               .firstName(user.getFirstName().value())
               .lastName(user.getLastName().value())
               .email(user.getEmail().value())
               .authorities(RestAuthority.fromSet(user.getAuthorities()))
               .build();
    }

}
