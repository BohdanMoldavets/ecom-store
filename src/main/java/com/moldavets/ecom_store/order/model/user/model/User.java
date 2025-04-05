package com.moldavets.ecom_store.order.model.user.model;

import com.moldavets.ecom_store.order.model.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class User {

    private UserLastName lastName;

    private UserFirstName firstName;

    private UserEmail email;

    private UserPublicId userPublicId;

    private UserImageUrl imageUrl;

    private Instant lastModifiedDate;

    private Instant createdDate;

    private Set<Authority> authorities;

    private Long dbId;

    private UserAddress userAddress;

    private Instant lastSeen;

    public static User fromTokenAttributes(Map<String, Object> attributes, List<String> rolesFromAccessToken) {
        UserBuilder userBuilder = User.builder();

        if(attributes.containsKey("preferred_email")) {
            userBuilder.email(new UserEmail(attributes.get("preferred_email").toString()));
        }

        if(attributes.containsKey("last_name")) {
            userBuilder.lastName(new UserLastName(attributes.get("last_name").toString()));
        }

        if(attributes.containsKey("first_name")) {
            userBuilder.firstName(new UserFirstName(attributes.get("first_name").toString()));
        }

        if(attributes.containsKey("picture")) {
            userBuilder.imageUrl(new UserImageUrl(attributes.get("picture").toString()));
        }

        if(attributes.containsKey("last_signed_in")) {
            userBuilder.lastSeen(Instant.parse(attributes.get("last_signed_in").toString()));
        }

        Set<Authority> authorities = rolesFromAccessToken.stream()
                .map(authority -> Authority.builder().name(new AuthorityName(authority)).build())
                .collect(Collectors.toSet());

        userBuilder.authorities(authorities);
        return userBuilder.build();
    }

    public void updateFromUser(User user) {
        this.email = user.email;
        this.imageUrl = user.imageUrl;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
    }

    public void initFieldsForSignup() {
        this.userPublicId = new UserPublicId(UUID.randomUUID());
    }

    private void validateRequiredFields() {
        Objects.requireNonNull(lastName, "LastName is required");
        Objects.requireNonNull(firstName, "First name is required");
        Objects.requireNonNull(email, "Email is required");
        Objects.requireNonNull(authorities, "Authorities is required");
    }

    public UserLastName getLastName() {
        return lastName;
    }

    public UserFirstName getFirstName() {
        return firstName;
    }

    public UserEmail getEmail() {
        return email;
    }

    public UserPublicId getUserPublicId() {
        return userPublicId;
    }

    public UserImageUrl getImageUrl() {
        return imageUrl;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public Long getDbId() {
        return dbId;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }
}
