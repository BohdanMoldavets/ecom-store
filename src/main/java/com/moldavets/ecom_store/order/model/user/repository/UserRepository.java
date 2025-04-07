package com.moldavets.ecom_store.order.model.user.repository;

import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.order.model.user.vo.UserAddressToUpdate;
import com.moldavets.ecom_store.order.model.user.vo.UserEmail;
import com.moldavets.ecom_store.order.model.user.vo.UserPublicId;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> get(UserPublicId userPublicId);

    Optional<User> getOneByEmail(UserEmail userEmail);

    void updateAddress(UserPublicId userPublicId, UserAddressToUpdate userAddress);
}
