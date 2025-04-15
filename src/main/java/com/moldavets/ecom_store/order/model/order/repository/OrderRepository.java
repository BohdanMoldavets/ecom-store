package com.moldavets.ecom_store.order.model.order.repository;

import com.moldavets.ecom_store.order.model.order.model.Order;

public interface OrderRepository {
    void save(Order order);
}
