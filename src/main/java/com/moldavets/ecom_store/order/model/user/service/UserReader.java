package com.moldavets.ecom_store.order.model.user.service;

import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.order.model.user.repository.UserRepository;
import com.moldavets.ecom_store.order.model.user.vo.UserEmail;
import com.moldavets.ecom_store.order.model.user.vo.UserPublicId;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

public class UserReader {

    private final UserRepository userRepository;

    public UserReader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getByEmail(UserEmail userEmail) {
        return userRepository.getOneByEmail(userEmail);
    }

    public Optional<User> getByPublic(UserPublicId userPublicId) {
        return userRepository.get(userPublicId);
    }

}
