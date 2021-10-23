package com.allowexactly.demo.web;

import com.allowexactly.demo.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests to "/api" endpoints.
 * @see com.allowexactly.demo.security.SecurityConfig to see how these endpoints are protected.
 */
@Controller
public class UIController {

    @RequestMapping(value={"/member-page"})
    public String MemberPage() {
        return "index.html";
    }
}
