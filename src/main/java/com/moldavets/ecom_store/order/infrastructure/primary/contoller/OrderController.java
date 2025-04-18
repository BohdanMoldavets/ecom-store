package com.moldavets.ecom_store.order.infrastructure.primary.contoller;

import com.moldavets.ecom_store.common.excpetion.CartPaymentException;
import com.moldavets.ecom_store.order.infrastructure.primary.model.*;
import com.moldavets.ecom_store.order.model.order.model.*;
import com.moldavets.ecom_store.order.model.order.vo.StripeSessionId;
import com.moldavets.ecom_store.order.model.user.vo.UserAddress;
import com.moldavets.ecom_store.order.model.user.vo.UserAddressToUpdate;
import com.moldavets.ecom_store.order.service.OrderApplicationService;
import com.moldavets.ecom_store.product.vo.UserPublicId;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Address;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Value("${application.stripe.webhook-secret}")
    private String webhookSecret;

    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @GetMapping("/get-cart-details")
    public ResponseEntity<RestDetailCartResponse> getDetails(@RequestParam List<UUID> productIds) {
        List<DetailCartItemRequest> itemRequests = productIds.stream()
                .map(uuid -> new DetailCartItemRequest(new UserPublicId(uuid), 1))
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

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhookStripe(@RequestBody String paymentEvent,
                                              @RequestHeader("Stripe-Signature") String stripeSignature) {
        Event event = null;

        try {
            event = Webhook.constructEvent(
                    paymentEvent, stripeSignature, webhookSecret
            );
        } catch (SignatureVerificationException sve) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<StripeObject> rawPaymentEventOpt = event.getDataObjectDeserializer().getObject();

        switch (event.getType()){
            case "checkout.session.completed":
                handleCheckoutSessionCompleted(rawPaymentEventOpt.orElseThrow());
                break;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<RestOrderRead>> getOrdersForConnectedUser(Pageable pageable){
        Page<Order> orders = orderApplicationService.findOrdersForConnectedUser(pageable);
        PageImpl<RestOrderRead> restOrderReads = new PageImpl<>(
                orders.stream().map(RestOrderRead::from).toList(),
                pageable,
                orders.getTotalElements()
        );
        return new ResponseEntity<>(restOrderReads, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<RestOrderReadAdmin>> getOrdersForAdmin(Pageable pageable){
        Page<Order> orders = orderApplicationService.findOrdersForAdmin(pageable);
        PageImpl<RestOrderReadAdmin> restOrderReads = new PageImpl<>(
                orders.stream().map(RestOrderReadAdmin::from).toList(),
                pageable,
                orders.getTotalElements()
        );
        return new ResponseEntity<>(restOrderReads, HttpStatus.OK);
    }

    private void handleCheckoutSessionCompleted(StripeObject rawStripeObject) {
        if(rawStripeObject instanceof Session session) {
            Address address = session.getCustomerDetails().getAddress();

            UserAddress userAddress = UserAddress.builder()
                    .city(address.getCity())
                    .country(address.getCountry())
                    .zipCode(address.getPostalCode())
                    .street(address.getLine1())
                    .build();

            UserAddressToUpdate userAddressToUpdate = UserAddressToUpdate.builder()
                    .userAddress(userAddress)
                    .publicId(new com.moldavets.ecom_store.order.model.user.vo.UserPublicId(UUID.fromString(session.getMetadata().get("user_public_id"))))
                    .build();

            StripeSessionInformation sessionInformation = StripeSessionInformation.builder()
                    .userAddress(userAddressToUpdate)
                    .stripeSessionId(new StripeSessionId(session.getId()))
                    .build();

            orderApplicationService.updateOrder(sessionInformation);
        }
    }
}
