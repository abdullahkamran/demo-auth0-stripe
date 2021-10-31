package com.allowexactly.demo.stripe;

import com.stripe.Stripe;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SubscriptionService {

    @Value("${stripe.keys.private}")
    private String secretStripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretStripeKey;
    }

    public SessionCreateParams createSubscriptionParams(String customerId, String priceId) {

        // Create new Checkout Session for the order
        // Other optional params include:
        // setBillingAddressCollection - to display billing address details on the page
        // setCustomer - if you have an existing Stripe Customer ID
        // setCustomerEmail - lets you prefill the email input in the form

        return new SessionCreateParams.Builder()
                .setCustomer(customerId)
                .setSuccessUrl("http://localhost:3000/member-page?payment-success")//to be routed after success
                .setCancelUrl("http://localhost:3000/member-page?payment-canceled")//to be routed in case of cancellation
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .addLineItem(new SessionCreateParams.LineItem.Builder()
                        .setQuantity(1L)
                        .setPrice(priceId)
                        .build()
                ).build();

    }
}
