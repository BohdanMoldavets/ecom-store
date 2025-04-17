package com.moldavets.ecom_store.order.infrastructure.primary.model;

import com.moldavets.ecom_store.order.model.order.model.OrderedProduct;
import com.moldavets.ecom_store.order.model.order.vo.OrderPrice;
import com.moldavets.ecom_store.order.model.order.vo.OrderQuantity;
import com.moldavets.ecom_store.product.vo.ProductName;
import lombok.Builder;

import java.util.List;

@Builder
public record RestOrderedItemRead(long quantity,
                                  double price,
                                  String name) {

    public static RestOrderedItemRead from(OrderedProduct orderedProduct) {
        return RestOrderedItemRead.builder()
                .name(orderedProduct.getName().value())
                .quantity(orderedProduct.getQuantity().value())
                .price(orderedProduct.getPrice().value())
                .build();
    }

    public static List<RestOrderedItemRead> from(List<OrderedProduct> orderedProducts) {
        return orderedProducts.stream()
                .map(RestOrderedItemRead::from)
                .toList();
    }

}
