package com.moldavets.ecom_store.order.infrastructure.secondary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Builder
public class OrderedProductEntityPk implements Serializable {

    @ManyToOne
    @JoinColumn(name = "fk_order", nullable = false)
    private OrderEntity order;

    @Column(name = "fk_product")
    private UUID productPublicId;

    public OrderedProductEntityPk() {
    }

    public OrderedProductEntityPk(OrderEntity order, UUID productPublicId) {
        this.order = order;
        this.productPublicId = productPublicId;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public UUID getProductPublicId() {
        return productPublicId;
    }

    public void setProductPublicId(UUID productPublicId) {
        this.productPublicId = productPublicId;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        OrderedProductEntityPk that = (OrderedProductEntityPk) object;
        return Objects.equals(productPublicId, that.productPublicId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productPublicId);
    }
}
