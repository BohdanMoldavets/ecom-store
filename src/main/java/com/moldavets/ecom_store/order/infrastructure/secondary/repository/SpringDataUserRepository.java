package com.moldavets.ecom_store.order.infrastructure.secondary.repository;

import com.moldavets.ecom_store.order.infrastructure.secondary.entity.UserEntity;
import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.order.model.user.repository.UserRepository;
import com.moldavets.ecom_store.order.model.user.vo.UserAddressToUpdate;
import com.moldavets.ecom_store.order.model.user.vo.UserEmail;
import com.moldavets.ecom_store.order.model.user.vo.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringDataUserRepository implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> get(UserPublicId userPublicId) {
        return jpaUserRepository.findOneByPublicId(userPublicId.value())
                .map(UserEntity::toUser);
    }

    @Override
    public Optional<User> getOneByEmail(UserEmail userEmail) {
        return jpaUserRepository.findOneByEmail(userEmail.value())
                .map(UserEntity::toUser);
    }

    @Override
    public void save(User user) {
        if(user.getDbId() != null) {
            Optional<UserEntity> tempUser = jpaUserRepository.findById(user.getDbId());
            if(tempUser.isPresent()) {
                UserEntity userToUpdate = tempUser.get();
                userToUpdate.updateFromUser(user);
                jpaUserRepository.saveAndFlush(userToUpdate);
            }
        } else {
            jpaUserRepository.save(UserEntity.fromUser(user));
        }
    }

    @Override
    public void updateAddress(UserPublicId userPublicId, UserAddressToUpdate userAddress) {
        jpaUserRepository.updateAddress(
                userPublicId.value(),
                userAddress.userAddress().street(),
                userAddress.userAddress().city(),
                userAddress.userAddress().country(),
                userAddress.userAddress().zipCode()
        );
    }
}
