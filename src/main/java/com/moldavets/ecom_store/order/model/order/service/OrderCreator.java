package com.moldavets.ecom_store.order.model.order.service;

import com.moldavets.ecom_store.order.infrastructure.secondary.service.stripe.StripeService;
import com.moldavets.ecom_store.order.model.order.model.DetailCartItemRequest;
import com.moldavets.ecom_store.order.model.order.model.Order;
import com.moldavets.ecom_store.order.model.order.model.OrderedProduct;
import com.moldavets.ecom_store.order.model.order.repository.OrderRepository;
import com.moldavets.ecom_store.order.model.order.vo.StripeSessionId;
import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.product.model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderCreator {

    private final OrderRepository orderRepository;
    private final StripeService stripeService;

    public OrderCreator(OrderRepository orderRepository, StripeService stripeService) {
        this.orderRepository = orderRepository;
        this.stripeService = stripeService;
    }

    public StripeSessionId create(List<Product> productsInformation,
                                  List<DetailCartItemRequest> items,
                                  User connectedUser) {

        StripeSessionId stripeSessionId = this.stripeService.createPayment(connectedUser, productsInformation, items);

        List<OrderedProduct> orderedProducts = new ArrayList<>();

        for (DetailCartItemRequest item : items) {
            Product productDetail = productsInformation.stream()
                    .filter(product -> product.getPublicId().id().equals(item.productId().id()))
                    .findFirst()
                    .orElseThrow();

            OrderedProduct orderedProduct = OrderedProduct.create(item.quantity(), productDetail);
            orderedProducts.add(orderedProduct);
        }
        Order order = Order.create(connectedUser, orderedProducts, stripeSessionId);
        orderRepository.save(order);

        return stripeSessionId;
    }
}
