package com.moldavets.ecom_store.order.model.order.model;

import lombok.Builder;

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
