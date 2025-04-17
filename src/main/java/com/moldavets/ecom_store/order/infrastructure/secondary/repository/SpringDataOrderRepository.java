package com.moldavets.ecom_store.order.infrastructure.secondary.repository;

import com.moldavets.ecom_store.order.infrastructure.secondary.entity.OrderEntity;
import com.moldavets.ecom_store.order.model.order.model.Order;
import com.moldavets.ecom_store.order.model.order.model.StripeSessionInformation;
import com.moldavets.ecom_store.order.model.order.repository.OrderRepository;
import com.moldavets.ecom_store.order.model.order.vo.OrderStatus;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringDataOrderRepository implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final JpaOrderedProductRepository jpaOrderedProductRepository;

    public SpringDataOrderRepository(JpaOrderRepository jpaOrderRepository, JpaOrderedProductRepository jpaOrderedProductRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.jpaOrderedProductRepository = jpaOrderedProductRepository;
    }

    @Override
    public void save(Order order) {
        OrderEntity orderEntityToCreate = OrderEntity.from(order);
        OrderEntity storedOrderEntity = jpaOrderRepository.save(orderEntityToCreate);

        storedOrderEntity.getOrderedProducts()
                .forEach(orderedProductEntity ->
                        orderedProductEntity.getId().setOrder(orderEntityToCreate));

        jpaOrderedProductRepository.saveAll(storedOrderEntity.getOrderedProducts());
    }

    @Override
    public void updateStatusByPublicId(OrderStatus orderStatus, PublicId publicId) {
        jpaOrderRepository.updateStatusByPublicId(orderStatus, publicId.id());
    }

    @Override
    public Optional<Order> findByStripeSessionId(StripeSessionInformation stripeSessionInformation) {
        return jpaOrderRepository.findByStripeSessionId(stripeSessionInformation.stripeSessionId().value())
                .map(OrderEntity::to);
    }
}
