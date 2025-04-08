package com.moldavets.ecom_store.order.infrastructure.secondary.entity;

import com.moldavets.ecom_store.common.jpa.AbstractAuditingEntity;
import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.order.model.user.vo.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "ecommerce_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String fistName;

    @Column(name = "email")
    private String email;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "public_id")
    private UUID publicId;

    @Column(name = "address_street")
    private String addressStreet;

    @Column(name = "address_city")
    private String addressCity;

    @Column(name = "address_zip_code")
    private String addressZipCode;

    @Column(name = "address_country")
    private String addressCountry;

    @Column(name = "last_seen")
    private Instant lastSeen;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_name", referencedColumnName = "name")
    )
    private Set<AuthorityEntity> authorities = new HashSet<>();

    public void updateFromUser(User user) {
        this.email = user.getEmail().value();
        this.lastName = user.getLastName().value();
        this.fistName = user.getFirstName().value();
        this.imageURL = user.getImageUrl().value();
        this.lastSeen = user.getLastSeen();
    }

    public static UserEntity fromUser(User user) {
        UserEntityBuilder userEntityBuilder = UserEntity.builder();
        if(user.getImageUrl() != null) {
            userEntityBuilder.imageURL(user.getImageUrl().value());
        }

        if(user.getPublicId() != null) {
            userEntityBuilder.publicId(user.getPublicId().value());
        }

        if(user.getUserAddress() != null) {
            userEntityBuilder.addressCity(user.getUserAddress().city());
            userEntityBuilder.addressCountry(user.getUserAddress().country());
            userEntityBuilder.addressZipCode(user.getUserAddress().zipCode());
            userEntityBuilder.addressStreet(user.getUserAddress().street());
        }

        return userEntityBuilder
                .authorities(AuthorityEntity.from(user.getAuthorities()))
                .email(user.getEmail().value())
                .fistName(user.getFirstName().value())
                .lastName(user.getLastName().value())
                .lastSeen(user.getLastSeen())
                .id(user.getDbId())
                .build();
    }

    public static User toUser(UserEntity userEntity) {
        User.UserBuilder userBuilder = User.builder();

        if(userEntity.getImageURL() != null) {
            userBuilder.imageUrl(new UserImageUrl(userEntity.getImageURL()));
        }

        if(userEntity.getAddressStreet() != null) {
            userBuilder.userAddress(UserAddress.builder()
                    .city(userEntity.getAddressCity())
                    .country(userEntity.getAddressCountry())
                    .zipCode(userEntity.getAddressZipCode())
                    .street(userEntity.getAddressStreet())
                    .build());
        }

        return userBuilder
                .email(new UserEmail(userEntity.getEmail()))
                .lastName(new UserLastName(userEntity.getLastName()))
                .firstName(new UserFirstName(userEntity.getFistName()))
                .authorities(AuthorityEntity.to(userEntity.getAuthorities()))
                .publicId(new UserPublicId(userEntity.getPublicId()))
                .lastModifiedDate(userEntity.getLastModifiedDate())
                .createdDate(userEntity.getCreatedDate())
                .dbId(userEntity.getId())
                .build();
    }

    public static Set<UserEntity> from(List<User> users) {
        return users.stream().map(UserEntity::fromUser).collect(Collectors.toSet());
    }

    public static Set<User> to(List<UserEntity> users) {
        return users.stream().map(UserEntity::toUser).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressZipCode() {
        return addressZipCode;
    }

    public void setAddressZipCode(String addressZipCode) {
        this.addressZipCode = addressZipCode;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Set<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        UserEntity that = (UserEntity) object;
        return Objects.equals(publicId, that.publicId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(publicId);
    }
}
