package com.moldavets.ecom_store.order.model.order.repository;

import com.moldavets.ecom_store.order.model.order.model.Order;
import com.moldavets.ecom_store.order.model.order.model.StripeSessionInformation;
import com.moldavets.ecom_store.order.model.order.vo.OrderStatus;
import com.moldavets.ecom_store.product.vo.PublicId;

import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    void updateStatusByPublicId(OrderStatus orderStatus, PublicId publicId);

    Optional<Order> findByStripeSessionId(StripeSessionInformation stripeSessionInformation);
}
