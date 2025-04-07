package com.moldavets.ecom_store.order.model.user.service;

import com.moldavets.ecom_store.common.auth.service.AuthenticatedUser;
import com.moldavets.ecom_store.order.infrastructure.secondary.service.KindeService;
import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.order.model.user.repository.UserRepository;
import com.moldavets.ecom_store.order.model.user.vo.UserAddressToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserSynchronizer {

    private final UserRepository userRepository;
    private final KindeService kindeService;

    private static final String UPDATE_AT_KEY = "last_signed_in";

    public UserSynchronizer(UserRepository userRepository, KindeService kindeService) {
        this.userRepository = userRepository;
        this.kindeService = kindeService;
    }

    public void syncWithIdp(Jwt jwt, boolean forceResync) {
        Map<String, Object> claims = jwt.getClaims();
        List<String> rolesFromToken = AuthenticatedUser.extractRolesFromToken(jwt);
        Map<String, Object> userInfo = kindeService.getUserInfo(claims.get("sub").toString());

        User user = User.fromTokenAttributes(userInfo, rolesFromToken);
        Optional<User> storedUser = userRepository.getOneByEmail(user.getEmail());

        if(storedUser.isPresent()) {
            if(claims.get(UPDATE_AT_KEY) != null) {
                Instant lastModifiedDate = storedUser.orElseThrow().getLastModifiedDate();
                Instant idpModifiedDate = Instant.ofEpochSecond((Integer)claims.get(UPDATE_AT_KEY));

                if(idpModifiedDate.isAfter(lastModifiedDate) || forceResync) {
                    updateUser(user, storedUser.get());
                }
            }
        } else {
            user.initFieldsForSignup();
            userRepository.save(user);
        }
    }

    public void updateUserAddress(UserAddressToUpdate userAddressToUpdate) {
        userRepository.updateAddress(userAddressToUpdate.publicId(), userAddressToUpdate);
    }

    private void updateUser(User user, User storedUser) {
        storedUser.updateFromUser(user);
        userRepository.save(storedUser);
    }
}
