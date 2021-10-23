package com.allowexactly.demo.web;

import com.stripe.Stripe;
import com.stripe.param.CustomerCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CustomerService {

    @Value("${stripe.keys.private}")
    private String secretStripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretStripeKey;
    }

    public CustomerCreateParams createCustomerParams(String email) {
        // Create new Customer
        // Other optional params include:
        // setAddress
        // setName
        // setPhone

        return new CustomerCreateParams.Builder()
                .setEmail(email)
                .build();
    }
}
