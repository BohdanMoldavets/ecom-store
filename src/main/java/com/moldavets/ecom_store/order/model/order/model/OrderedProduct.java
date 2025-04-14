package com.moldavets.ecom_store.order.model.order.model;

import com.moldavets.ecom_store.order.model.order.vo.OrderPrice;
import com.moldavets.ecom_store.order.model.order.vo.OrderQuantity;
import com.moldavets.ecom_store.order.model.order.vo.ProductPublicId;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.vo.ProductName;
import lombok.Builder;

@Builder
public class OrderedProduct {

    private final ProductPublicId productPublicId;

    private final OrderPrice price;

    private final OrderQuantity quantity;

    private final ProductName name;

    public OrderedProduct(ProductPublicId productPublicId, OrderPrice price, OrderQuantity quantity, ProductName name) {
        this.productPublicId = productPublicId;
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }

    public static OrderedProduct create(long quantity, Product product) {
        return OrderedProduct.builder()
                .price(new OrderPrice(product.getPrice().value()))
                .quantity(new OrderQuantity(quantity))
                .name(new ProductName(product.getName().value()))
                .productPublicId(new ProductPublicId(product.getPublicId().id()))
                .build();
    }

    public ProductPublicId getProductPublicId() {
        return productPublicId;
    }

    public OrderPrice getPrice() {
        return price;
    }

    public OrderQuantity getQuantity() {
        return quantity;
    }

    public ProductName getName() {
        return name;
    }
}
