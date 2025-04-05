package com.moldavets.ecom_store.order.model.user.repository;

import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.order.model.user.vo.UserAddress;
import com.moldavets.ecom_store.order.model.user.vo.UserEmail;
import com.moldavets.ecom_store.order.model.user.vo.UserPublicId;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> get(UserPublicId userPublicId);

    Optional<User> getByEmail(UserEmail userEmail);

    void updateAddress(UserPublicId userPublicId, UserAddress userAddress);
}
