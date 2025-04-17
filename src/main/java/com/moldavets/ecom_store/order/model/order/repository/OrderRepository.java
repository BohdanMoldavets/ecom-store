package com.moldavets.ecom_store.order.model.order.repository;

import com.moldavets.ecom_store.order.model.order.model.Order;
import com.moldavets.ecom_store.order.model.order.model.StripeSessionInformation;
import com.moldavets.ecom_store.order.model.order.vo.OrderStatus;
import com.moldavets.ecom_store.product.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    void updateStatusByPublicId(OrderStatus orderStatus, UserPublicId publicId);

    Optional<Order> findByStripeSessionId(StripeSessionInformation stripeSessionInformation);

    Page<Order> findAllByUserPublicId(UserPublicId publicId, Pageable pageable);
}
