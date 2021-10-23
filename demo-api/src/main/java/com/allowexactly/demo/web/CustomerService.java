package com.allowexactly.demo.web;

import com.stripe.param.CustomerCreateParams;

public class CustomerService extends ParentService{


    public CustomerService(String stripeKey){
        super(stripeKey);
    }

    public CustomerCreateParams createCustomerParams(String email){
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
