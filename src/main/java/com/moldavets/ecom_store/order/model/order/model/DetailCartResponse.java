package com.moldavets.ecom_store.order.model.order.model;

import com.moldavets.ecom_store.product.model.ProductCart;
import lombok.Builder;

import java.util.List;

@Builder
public class DetailCartResponse {

    List<ProductCart> products;

    public DetailCartResponse(List<ProductCart> products) {
        this.products = products;
    }

    public List<ProductCart> getProducts() {
        return products;
    }

    public void setProducts(List<ProductCart> products) {
        this.products = products;
    }
}
