package com.moldavets.ecom_store.product.service;

import com.moldavets.ecom_store.order.model.order.model.OrderProductQuantity;
import com.moldavets.ecom_store.order.model.order.model.OrderedProduct;
import com.moldavets.ecom_store.product.repository.ProductRepository;

import java.util.List;

public class ProductUpdater {

    private final ProductRepository productRepository;

    public ProductUpdater(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void updateProductQuantity(List<OrderProductQuantity> orderProductQuantities) {
        for (OrderProductQuantity orderProductQuantity : orderProductQuantities) {
            productRepository.updateQuantity(orderProductQuantity.publicId(), orderProductQuantity.quantity().value());
        }
    }
}
