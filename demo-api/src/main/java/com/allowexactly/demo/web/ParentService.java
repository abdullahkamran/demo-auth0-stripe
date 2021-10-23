package com.allowexactly.demo.web;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;

public class ParentService {

    public ParentService(String stripeKey){
        Stripe.apiKey = stripeKey;
    }
}
