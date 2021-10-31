package com.allowexactly.demo.stripe;

import com.allowexactly.demo.model.Product;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Handles requests to "/api" endpoints.
 *
 * @see com.allowexactly.demo.security.SecurityConfig to see how these endpoints are protected.
 */
@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")
public class StripeController {

  final SubscriptionService subscriptionService;
  final CustomerService customerService;
  final ProductService productService;

  public StripeController(ProductService productService, CustomerService customerService, SubscriptionService subscriptionService) {
    this.productService = productService;
    this.customerService = customerService;
    this.subscriptionService = subscriptionService;
  }

  @GetMapping(value = "/getAllProducts")
  public List<Product> getAllProductsAPI() throws StripeException {
    return productService.getAllProducts();
  }

  @PostMapping(value = "/subscriptionCheckout")
  public String startSubscription(@RequestBody Map<String, String> requestBody) throws StripeException {
    String customerEmail = requestBody.get("customerEmail");
    String priceId = requestBody.get("priceId");
    Optional<Customer> existingCustomer = customerService.getExistingCustomer(customerEmail);
    String customerId = existingCustomer.isPresent()
        ? existingCustomer.get().getId()
        : customerService.createCustomer(customerEmail);
    SessionCreateParams params = subscriptionService.createSubscriptionParams(customerId, priceId);
    return Session.create(params).getUrl(); //redirect to this url
  }

  @PostMapping(value = "/createCustomerPortalSession")
  public ResponseEntity createCustomerPortalSession(@RequestBody Map<String, String> requestBody) throws StripeException {
    String customerEmail = requestBody.get("customerEmail");
    Optional<Customer> existingCustomer = customerService.getExistingCustomer(customerEmail);
    if (existingCustomer.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
    }
    String customerId = existingCustomer.get().getId();
    com.stripe.model.billingportal.Session session = customerService.requestCustomerPortal(customerId);
    return ResponseEntity.ok(session.getUrl()); //redirect to this url
  }

  @ExceptionHandler(StripeException.class)
  public String handleError(Model model, StripeException ex) {
    return ex.toString();
  }
}
