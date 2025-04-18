package com.moldavets.ecom_store.order.service;

import com.moldavets.ecom_store.common.auth.service.AuthenticatedUser;
import com.moldavets.ecom_store.order.infrastructure.secondary.service.kinde.KindeService;
import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.order.model.user.repository.UserRepository;
import com.moldavets.ecom_store.order.model.user.service.UserReader;
import com.moldavets.ecom_store.order.model.user.service.UserSynchronizer;
import com.moldavets.ecom_store.order.model.user.vo.UserAddressToUpdate;
import com.moldavets.ecom_store.order.model.user.vo.UserEmail;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserApplicationService {

    private final UserSynchronizer userSynchronizer;
    private final UserReader userReader;

    public UserApplicationService(UserRepository userRepository, KindeService kindeService) {
        this.userSynchronizer = new UserSynchronizer(userRepository, kindeService);
        this.userReader = new UserReader(userRepository);
    }

    @Transactional
    public User getAuthenticatedUserWithSync(Jwt jwtToken, boolean forceResync) {
        userSynchronizer.syncWithIdp(jwtToken, forceResync);
        return userReader.getByEmail(new UserEmail(AuthenticatedUser.username().get()))
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public User getAuthenticatedUser() {
        return userReader.getByEmail(new UserEmail(AuthenticatedUser.username().get()))
                .orElseThrow();
    }

    @Transactional
    public void updateAddress(UserAddressToUpdate userAddressToUpdate) {
        userSynchronizer.updateUserAddress(userAddressToUpdate);
    }
}
