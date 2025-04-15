package com.moldavets.ecom_store.order.service;

import com.moldavets.ecom_store.order.model.order.model.DetailCartItemRequest;
import com.moldavets.ecom_store.order.model.order.model.DetailCartRequest;
import com.moldavets.ecom_store.order.model.order.model.DetailCartResponse;
import com.moldavets.ecom_store.order.model.order.service.CartReader;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.service.ProductApplicationService;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderApplicationService {

    private final ProductApplicationService productApplicationService;
    private final CartReader cartReader;

    public OrderApplicationService(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
        this.cartReader = new CartReader();
    }

    @Transactional(readOnly = true)
    public DetailCartResponse getCartDetails(DetailCartRequest detailCartRequest) {
        List<PublicId> publicIds = detailCartRequest.items().stream()
                .map(DetailCartItemRequest::productId)
                .toList();
        List<Product> productsInformation = productApplicationService.getProductsByPublicIdsIn(publicIds);

        return cartReader.getDetails(productsInformation);
    }
}
