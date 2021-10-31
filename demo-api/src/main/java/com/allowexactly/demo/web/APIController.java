package com.allowexactly.demo.web;

import com.allowexactly.demo.model.Message;
import com.allowexactly.demo.model.Product;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles requests to "/api" endpoints.
 *
 * @see com.allowexactly.demo.security.SecurityConfig to see how these endpoints are protected.
 */
@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")
public class APIController {

    @GetMapping(value = "/public")
    public Message publicEndpoint() {
        return new Message("All good. You DO NOT need to be authenticated to call /api/public.");
    }

    @GetMapping(value = "/private")
    public Message privateEndpoint() {
        return new Message("All good. You can see this because you are Authenticated.");
    }

    @GetMapping(value = "/posts")
    public Message getPosts() {
        return new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:posts' scope");
    }

    @GetMapping(value = "/messages")
    public Message getMessages() {
        return new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        return ex.toString();
    }
}
