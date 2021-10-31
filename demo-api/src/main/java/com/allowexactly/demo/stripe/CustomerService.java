package com.allowexactly.demo.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.billingportal.Session;
import com.stripe.param.CustomerCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {

    @Value("${stripe.keys.private}")
    private String secretStripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretStripeKey;
    }

    public Optional<Customer> getExistingCustomer(String email) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        List<Customer> customers = Customer.list(params).getData();
        return customers.size() > 0
            ? Optional.of(customers.get(0)) : Optional.empty();
    }

    public Session requestCustomerPortal(String customerId) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);
        params.put(
            "return_url",
            "http://localhost:3000/member-page?customer-portal-redirect"
        );
        Session session = Session.create(params);
        return session;
    }

    public String createCustomer(String email) throws StripeException {
        CustomerCreateParams params = createCustomerParams(email);
        Customer customer = Customer.create(params);
        return customer.getId();
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
