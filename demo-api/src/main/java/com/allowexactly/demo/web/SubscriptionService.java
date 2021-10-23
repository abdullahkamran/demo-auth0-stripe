package com.allowexactly.demo.web;

import com.stripe.param.checkout.SessionCreateParams;

public class SubscriptionService extends ParentService{

    public SubscriptionService(String stripeKey){
        super(stripeKey);
    }

    public SessionCreateParams createSubscriptionParams(String subscriptionId){

        // Create new Checkout Session for the order
        // Other optional params include:
        // setBillingAddressCollection - to display billing address details on the page
        // setCustomer - if you have an existing Stripe Customer ID
        // setCustomerEmail - lets you prefill the email input in the form

        return new SessionCreateParams.Builder()
                .setSuccessUrl("")//to be routed after success
                .setCancelUrl("")//to be routed in case of cancellation
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .addLineItem(new SessionCreateParams.LineItem.Builder()
                        .setQuantity(1L)
                        .setPrice(subscriptionId)
                        .build()
                ).build();

    }
}
