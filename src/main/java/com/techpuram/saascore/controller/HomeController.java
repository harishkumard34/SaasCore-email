package com.techpuram.saascore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping(value = {"/{path:[^\\.]*}", "/"})
    public String index(HttpServletRequest request) {
    	String requestURI = request.getRequestURI();

        // Exclude static files and API paths
        if (requestURI.startsWith("/build/") || requestURI.startsWith("/static/") || requestURI.startsWith("/v1/")) {
            return null; // Let Spring handle these requests
        }
        return "forward:/index.html"; // Forward other requests to index.html
    }
}