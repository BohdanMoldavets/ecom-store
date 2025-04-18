package com.moldavets.ecom_store.order.infrastructure.primary.model;

import com.moldavets.ecom_store.product.infrastructure.primary.model.RestPicture;
import com.moldavets.ecom_store.product.infrastructure.primary.model.RestProduct;
import com.moldavets.ecom_store.product.model.ProductCart;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RestProductCart(String name,
                              double price,
                              String brand,
                              RestPicture picture,
                              int quantity,
                              UUID publicId) {

    public static RestProductCart from(ProductCart productCart) {
        return RestProductCart.builder()
                .name(productCart.getName().value())
                .price(productCart.getPrice().value())
                .brand(productCart.getBrand().value())
                .picture(RestPicture.from(productCart.getPicture()))
                .publicId(productCart.getPublicId().id())
                .build();
    }

    public static List<RestProductCart> from(List<ProductCart> productCarts) {
        return productCarts.stream()
                .map(RestProductCart::from)
                .toList();
    }

}
