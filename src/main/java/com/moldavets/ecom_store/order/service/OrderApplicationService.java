package com.moldavets.ecom_store.order.service;

import com.moldavets.ecom_store.order.infrastructure.secondary.service.stripe.StripeService;
import com.moldavets.ecom_store.order.model.order.model.DetailCartItemRequest;
import com.moldavets.ecom_store.order.model.order.model.DetailCartRequest;
import com.moldavets.ecom_store.order.model.order.model.DetailCartResponse;
import com.moldavets.ecom_store.order.model.order.repository.OrderRepository;
import com.moldavets.ecom_store.order.model.order.service.CartReader;
import com.moldavets.ecom_store.order.model.order.service.OrderCreator;
import com.moldavets.ecom_store.order.model.order.vo.StripeSessionId;
import com.moldavets.ecom_store.order.model.user.model.User;
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
    private final UserApplicationService userApplicationService;
    private final OrderCreator orderCreator;

    public OrderApplicationService(ProductApplicationService productApplicationService,
                                   UserApplicationService userApplicationService,
                                   OrderRepository orderRepository,
                                   StripeService stripeService) {
        this.productApplicationService = productApplicationService;
        this.userApplicationService = userApplicationService;
        this.cartReader = new CartReader();
        this.orderCreator = new OrderCreator(orderRepository, stripeService);
    }

    @Transactional(readOnly = true)
    public DetailCartResponse getCartDetails(DetailCartRequest detailCartRequest) {
        List<PublicId> publicIds = detailCartRequest.items().stream()
                .map(DetailCartItemRequest::productId)
                .toList();
        List<Product> productsInformation = productApplicationService.getProductsByPublicIdsIn(publicIds);

        return cartReader.getDetails(productsInformation);
    }

    @Transactional
    public StripeSessionId createOrder(List<DetailCartItemRequest> items) {
        User authenticatedUser = userApplicationService.getAuthenticatedUser();

        List<PublicId> publicIds = items.stream().map(DetailCartItemRequest::productId).toList();
        List<Product> productInformation = productApplicationService.getProductsByPublicIdsIn(publicIds);

        return orderCreator.create(productInformation, items, authenticatedUser);
    }
}
