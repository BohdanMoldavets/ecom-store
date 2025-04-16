package com.moldavets.ecom_store.order.infrastructure.primary.contoller;

import com.moldavets.ecom_store.common.excpetion.CartPaymentException;
import com.moldavets.ecom_store.order.infrastructure.primary.model.RestCartItemRequest;
import com.moldavets.ecom_store.order.infrastructure.primary.model.RestDetailCartResponse;
import com.moldavets.ecom_store.order.infrastructure.primary.model.RestStripeSession;
import com.moldavets.ecom_store.order.model.order.model.DetailCartItemRequest;
import com.moldavets.ecom_store.order.model.order.model.DetailCartRequest;
import com.moldavets.ecom_store.order.model.order.model.DetailCartResponse;
import com.moldavets.ecom_store.order.model.order.vo.StripeSessionId;
import com.moldavets.ecom_store.order.service.OrderApplicationService;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @GetMapping("/get-cart-details")
    public ResponseEntity<RestDetailCartResponse> getDetails(@RequestParam List<UUID> productIds) {
        List<DetailCartItemRequest> itemRequests = productIds.stream()
                .map(uuid -> new DetailCartItemRequest(new PublicId(uuid), 1))
                .toList();

        DetailCartRequest detailCartRequest = DetailCartRequest.builder()
                .items(itemRequests)
                .build();

        DetailCartResponse cartDetail = orderApplicationService.getCartDetails(detailCartRequest);
        return new ResponseEntity<>(RestDetailCartResponse.from(cartDetail), HttpStatus.OK);
    }

    @PostMapping("/init-payment")
    public ResponseEntity<RestStripeSession> initPayment(@RequestBody List<RestCartItemRequest> items) {
        List<DetailCartItemRequest> detailCartItemRequests = RestCartItemRequest.to(items);
        try {
            StripeSessionId stripeSessionInformation = orderApplicationService.createOrder(detailCartItemRequests);
            RestStripeSession restStripeSession = RestStripeSession.from(stripeSessionInformation);
            return new ResponseEntity<>(restStripeSession, HttpStatus.OK);
        } catch (CartPaymentException cpe) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
