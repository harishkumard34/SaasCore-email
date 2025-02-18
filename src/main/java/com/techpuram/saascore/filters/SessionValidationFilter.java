package com.techpuram.saascore.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.techpuram.saascore.entity.Auth;
import com.techpuram.saascore.service.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class SessionValidationFilter{

    @Autowired
    private AuthService authService;

    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // Allow unauthenticated access to static resources and frontend files
        if (isStaticResource(requestURI) || "/".equals(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Skip session validation for signup and login endpoints
        if ("/v1/auth/signup".equals(requestURI) || "/v1/auth/login".equals(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Validate session for other requests
        String sessionId = httpRequest.getHeader("X-Session-ID");
        if (sessionId != null && !sessionId.isEmpty()) {
            Auth auth = authService.getAuthBySessionId(sessionId);
            if (auth != null) {
                chain.doFilter(request, response);
                return;
            }
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Invalid session ID.");
            return;
        }

        // If no session ID is provided
        httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        httpResponse.getWriter().write("Session ID is required.");
    }

    private boolean isStaticResource(String uri) {
        // Check for static resources served by Svelte and Spring Boot
        return uri.startsWith("/static/") ||
               uri.startsWith("/_app/") ||
               uri.startsWith("/build/") ||
               uri.startsWith("/favicon.") ||
               uri.endsWith(".js") ||
               uri.endsWith(".css") ||
               uri.endsWith(".png") ||
               uri.endsWith(".ico");
    }
}
