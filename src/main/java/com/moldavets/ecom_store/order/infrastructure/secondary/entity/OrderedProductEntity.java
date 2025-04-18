package com.moldavets.ecom_store.order.infrastructure.secondary.entity;

import com.moldavets.ecom_store.order.model.order.model.OrderedProduct;
import com.moldavets.ecom_store.order.model.order.vo.OrderPrice;
import com.moldavets.ecom_store.order.model.order.vo.OrderQuantity;
import com.moldavets.ecom_store.order.model.order.vo.ProductPublicId;
import com.moldavets.ecom_store.product.vo.ProductName;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "ordered_product")
@Builder
public class OrderedProductEntity {

    @EmbeddedId
    private OrderedProductEntityPk id;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "product_name", nullable = false)
    private String productName;

    public OrderedProductEntity() {
    }

    public OrderedProductEntity(OrderedProductEntityPk id, Double price, long quantity, String productName) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.productName = productName;
    }

    public static OrderedProductEntity from(OrderedProduct orderedProduct) {
        OrderedProductEntityPk compositeId = OrderedProductEntityPk.builder()
                .productPublicId(orderedProduct.getProductPublicId().value())
                .build();

        return OrderedProductEntity.builder()
                .price(orderedProduct.getPrice().value())
                .quantity(orderedProduct.getQuantity().value())
                .productName(orderedProduct.getName().value())
                .id(compositeId)
                .build();
    }

    public static List<OrderedProductEntity> from(List<OrderedProduct> orderedProducts) {
        return orderedProducts.stream()
                .map(OrderedProductEntity::from)
                .toList();
    }

    public static OrderedProduct to(OrderedProductEntity orderedProductEntity) {
        return OrderedProduct.builder()
                .productPublicId(new ProductPublicId(orderedProductEntity.getId().getProductPublicId()))
                .quantity(new OrderQuantity(orderedProductEntity.getQuantity()))
                .price(new OrderPrice(orderedProductEntity.getPrice()))
                .name(new ProductName(orderedProductEntity.getProductName()))
                .build();
    }

    public static List<OrderedProduct> to(List<OrderedProductEntity> orderedProductEntities) {
        return orderedProductEntities.stream()
                .map(OrderedProductEntity::to)
                .toList();
    }

    public OrderedProductEntityPk getId() {
        return id;
    }

    public void setId(OrderedProductEntityPk orderedProductEntity) {
        this.id = orderedProductEntity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        OrderedProductEntity that = (OrderedProductEntity) object;
        return quantity == that.quantity && Objects.equals(price, that.price) && Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, quantity, productName);
    }
}
