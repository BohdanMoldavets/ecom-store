package com.moldavets.ecom_store.order.model.order.service;

import com.moldavets.ecom_store.order.model.order.model.Order;
import com.moldavets.ecom_store.order.model.order.model.OrderProductQuantity;
import com.moldavets.ecom_store.order.model.order.model.OrderedProduct;
import com.moldavets.ecom_store.order.model.order.model.StripeSessionInformation;
import com.moldavets.ecom_store.order.model.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderUpdater {

    private final OrderRepository orderRepository;

    public OrderUpdater(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderedProduct> updateOrderFromStripe(StripeSessionInformation stripeSessionInformation) {
        Order order = orderRepository.findByStripeSessionId(stripeSessionInformation).orElseThrow();
        order.validatePayment();
        orderRepository.updateStatusByPublicId(order.getStatus(), order.getPublicId());
        return order.getOrderedProducts();
    }

    public List<OrderProductQuantity> computeQuantity(List<OrderedProduct> orderedProducts) {
        List<OrderProductQuantity> orderProductQuantities = new ArrayList<>();

        for (OrderedProduct orderProduct : orderedProducts) {
            OrderProductQuantity orderProductQuantity = OrderProductQuantity.builder()
                    .publicId(orderProduct.getProductPublicId())
                    .quantity(orderProduct.getQuantity())
                    .build();
            orderProductQuantities.add(orderProductQuantity);
        }
        return orderProductQuantities;
    }
}
