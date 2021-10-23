package com.allowexactly.demo.web;

import com.stripe.Stripe;
import com.stripe.param.ProductListParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ProductService {

    @Value("${stripe.keys.private}")
    private String secretStripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretStripeKey;
    }

    public ProductListParams createProductLstParams() {
        return new ProductListParams.Builder()
                .setLimit(10L)
                .build();
    }
}
