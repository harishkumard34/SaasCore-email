package com.techpuram.saascore.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.techpuram.saascore.config.CustomPrincipal;
import com.techpuram.saascore.config.JwtUtils;
import com.techpuram.saascore.config.RequestContext;
import com.techpuram.saascore.config.TenantContext;
import com.techpuram.saascore.entity.Auth;
import com.techpuram.saascore.entity.Users;
import com.techpuram.saascore.service.AuthService;
import com.techpuram.saascore.service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UnifiedAuthAndTenantFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AuthService authService;
    private final UserService userService;

    public UnifiedAuthAndTenantFilter(JwtUtils jwtUtils, AuthService authService, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();

        // Allow unauthenticated access to static resources and specific endpoints
        if (isPublicPath(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);
        String tenant = request.getHeader("X-PrivateTenant");
        boolean isAuthenticated = false;
        Users user = null;

        try {
            if (token != null) {
                // Parse and validate JWT
                Claims claims = jwtUtils.parseJwt(token);
                tenant = claims.get("schema", String.class);
                isAuthenticated = true;

                // Set user context from JWT
                String userId = claims.getSubject();
                if (userId != null) {
                    TenantContext.setCurrentTenant(tenant);
                    user = userService.getUserById(Long.parseLong(userId));
                    RequestContext.setCurrentUser(user);
                }
            } else {
                // Session validation fallback
                String sessionId = request.getHeader("X-Session-ID");
                if (sessionId != null && !sessionId.isEmpty()) {
                    Auth auth = authService.getAuthBySessionId(sessionId);
                    if (auth != null) {
                        isAuthenticated = true;
                        tenant = authService.getTenant(auth);
                        // Fetch user details from tenant schema
                        TenantContext.setCurrentTenant(tenant);
                        user = userService.getUserById(auth.getUserId());
                        RequestContext.setCurrentUser(user);
                    }
                }
            }

            if (isAuthenticated) {
                System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());
                if (tenant != null) {
                    TenantContext.setCurrentTenant(tenant); // Set tenant context
                }
                setAuthentication(user, tenant); // Set authentication in SecurityContext
                chain.doFilter(request, response);
                return;
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired authentication.");
            return;
        } finally {
            System.out.println("Authentication: 12121" + SecurityContextHolder.getContext().getAuthentication());
            TenantContext.clear();
            RequestContext.clear();
        }

        // If no token or session is valid
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication required.");
    }

    private boolean isPublicPath(String uri) {
        return uri.startsWith("/static/") || uri.startsWith("/_app/") ||
                uri.startsWith("/build/") || uri.startsWith("/favicon.") ||
                uri.equals("/") || uri.equals("/v1/auth/signup") || uri.equals("/v1/auth/login");
    }

    private String extractToken(HttpServletRequest request) {
        // Check the Authorization header
        String headerToken = request.getHeader("Authorization");
        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            return headerToken.substring(7);
        }

        // Check the SESSIONID cookie
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("SESSIONID".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private void setAuthentication(Users user, String tenant) {
        if (user != null) {
            // Create a custom principal with user details
            CustomPrincipal principal = new CustomPrincipal(user.getUserId(), user.getEmailId(), tenant);

            // Create authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,
                    null, null);

            // Set authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Set user in RequestContext for other layers
            RequestContext.setCurrentUser(user);
        }
    }

}
