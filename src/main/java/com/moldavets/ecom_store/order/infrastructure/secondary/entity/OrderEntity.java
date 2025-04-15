package com.moldavets.ecom_store.order.infrastructure.secondary.entity;

import com.moldavets.ecom_store.common.jpa.AbstractAuditingEntity;
import com.moldavets.ecom_store.order.model.order.model.Order;
import com.moldavets.ecom_store.order.model.order.model.OrderedProduct;
import com.moldavets.ecom_store.order.model.order.vo.OrderStatus;
import com.moldavets.ecom_store.product.vo.PublicId;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "order")
@Builder
public class OrderEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequenceGenerator")
    @SequenceGenerator(name = "orderSequenceGenerator", sequenceName = "order_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true)
    private UUID publicId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "stripe_session_id", nullable = false)
    private String stripeSessionId;

    @OneToMany(mappedBy = "id.order", cascade = CascadeType.REMOVE)
    private Set<OrderedProductEntity> orderedProducts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "fk_customer", nullable = false)
    private UserEntity user;

    public OrderEntity() {
    }

    public OrderEntity(Long id, UUID publicId, OrderStatus status, String stripeSessionId, Set<OrderedProductEntity> orderedProducts, UserEntity user) {
        this.id = id;
        this.publicId = publicId;
        this.status = status;
        this.stripeSessionId = stripeSessionId;
        this.orderedProducts = orderedProducts;
        this.user = user;
    }

    public static OrderEntity from(Order order) {
        Set<OrderedProductEntity> orderedProductsEntities =
                order.getOrderedProducts().stream()
                        .map(OrderedProductEntity::from)
                        .collect(Collectors.toSet());

        return OrderEntity.builder()
                .publicId(order.getPublicId().id())
                .status(order.getStatus())
                .stripeSessionId(order.getStripeId())
                .orderedProducts(orderedProductsEntities)
                .user(UserEntity.fromUser(order.getUser()))
                .build();
    }

    public static Order to(OrderEntity orderEntity) {
        Set<OrderedProduct> orderedProducts = orderEntity.getOrderedProducts().stream()
                .map(OrderedProductEntity::to)
                .collect(Collectors.toSet());

        return Order.builder()
                .publicId(new PublicId(orderEntity.getPublicId()))
                .status(orderEntity.getStatus())
                .stripeId(orderEntity.getStripeSessionId())
                .orderedProducts(orderedProducts.stream().toList())
                .user(UserEntity.toUser(orderEntity.getUser()))
                .build();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getStripeSessionId() {
        return stripeSessionId;
    }

    public void setStripeSessionId(String stripeSessionId) {
        this.stripeSessionId = stripeSessionId;
    }

    public Set<OrderedProductEntity> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(Set<OrderedProductEntity> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        OrderEntity that = (OrderEntity) object;
        return Objects.equals(publicId, that.publicId) && status == that.status && Objects.equals(stripeSessionId, that.stripeSessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicId, status, stripeSessionId);
    }
}
