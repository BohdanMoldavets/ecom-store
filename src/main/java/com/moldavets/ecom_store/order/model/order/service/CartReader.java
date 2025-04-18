package com.moldavets.ecom_store.order.model.order.service;

import com.moldavets.ecom_store.order.model.order.model.DetailCartResponse;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.model.ProductCart;

import java.util.List;

public class CartReader {

    public CartReader() {
    }

    public DetailCartResponse getDetails(List<Product> products) {
        List<ProductCart> cartProducts = products.stream().map(ProductCart::from).toList();
        return DetailCartResponse.builder()
                .products(cartProducts)
                .build();
    }
}
