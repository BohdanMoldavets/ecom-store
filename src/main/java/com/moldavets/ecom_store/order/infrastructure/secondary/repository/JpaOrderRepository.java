package com.moldavets.ecom_store.order.infrastructure.secondary.repository;

import com.moldavets.ecom_store.order.infrastructure.secondary.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {



}
