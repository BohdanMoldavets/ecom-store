package com.moldavets.ecom_store.order.infrastructure.secondary.service.stripe;

import com.moldavets.ecom_store.common.excpetion.CartPaymentException;
import com.moldavets.ecom_store.order.model.order.model.DetailCartItemRequest;
import com.moldavets.ecom_store.order.model.order.vo.StripeSessionId;
import com.moldavets.ecom_store.order.model.user.model.User;
import com.moldavets.ecom_store.product.model.Product;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StripeService {

    @Value("${application.stripe.api-key}")
    private String stripeApiKey;

    @Value("${application.client-base-url}")
    private String clientBaseUrl;

    public StripeService() {
    }

    @PostConstruct
    public void setApiKet() {
        Stripe.apiKey = stripeApiKey;
    }

    public StripeSessionId createPayment(User connectedUser,
                                         List<Product> productInformation,
                                         List<DetailCartItemRequest> items) {
        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .putMetadata("user_public_id", connectedUser.getPublicId().value().toString())
                .setCustomerEmail(connectedUser.getEmail().value())
                .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                .setSuccessUrl(clientBaseUrl + "/cart/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(clientBaseUrl + "/cart/failure");

        for (DetailCartItemRequest item : items) {
            Product productDetails = productInformation.stream()
                    .filter(p -> p.getPublicId().id().equals(item.productId().id()))
                    .findFirst()
                    .orElseThrow();

            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .putMetadata("product_id", productDetails.getPublicId().id().toString())
                    .setName(productDetails.getName().value())
                    .build();

            SessionCreateParams.LineItem.PriceData linePriceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setUnitAmountDecimal(BigDecimal.valueOf(Double.valueOf(productDetails.getPrice().value()).longValue() * 100))
                    .setProductData(productData)
                    .setCurrency("EUR")
                    .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(item.quantity())
                    .setPriceData(linePriceData)
                    .build();

            sessionBuilder.addLineItem(lineItem);
        }
        return createSession(sessionBuilder.build());
    }

    private StripeSessionId createSession(SessionCreateParams session) {
        try {
            Session sessionInformation = Session.create(session);
            return new StripeSessionId(sessionInformation.getId());
        } catch (StripeException se) {
            throw new CartPaymentException("Error while creating Stripe session");
        }
    }
}
