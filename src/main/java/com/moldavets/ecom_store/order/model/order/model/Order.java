package com.moldavets.ecom_store.order.model.order.model;

import com.moldavets.ecom_store.order.model.order.vo.OrderStatus;
import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.product.vo.PublicId;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public class Order {

    private OrderStatus status;

    private User user;

    private String stripeId;

    private PublicId publicId;

    List<OrderedProduct> orderedProducts;

    public Order(OrderStatus status, User user, String stripeId, PublicId publicId, List<OrderedProduct> orderedProducts) {
        this.status = status;
        this.user = user;
        this.stripeId = stripeId;
        this.publicId = publicId;
        this.orderedProducts = orderedProducts;
    }

    public static Order create(User connectedUser, List<OrderedProduct> orderedProducts, String stripeSessionId) {
        return Order.builder()
                .publicId(new PublicId(UUID.randomUUID()))
                .user(connectedUser)
                .status(OrderStatus.PENDING)
                .orderedProducts(orderedProducts)
                .stripeId(stripeSessionId)
                .build();
    }

    public void validatePayment() {
        this.status = OrderStatus.PAID;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public String getStripeId() {
        return stripeId;
    }

    public PublicId getPublicId() {
        return publicId;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }
}
