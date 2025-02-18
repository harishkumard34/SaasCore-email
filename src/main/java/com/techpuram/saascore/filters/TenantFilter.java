package com.techpuram.saascore.filters;

import java.io.IOException;

import com.techpuram.saascore.config.TenantContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class TenantFilter {

    public static final String PRIVATE_TENANT_HEADER = "X-PrivateTenant";
   
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        @SuppressWarnings("unused")
        HttpServletResponse res = (HttpServletResponse) response;

        // Check for tenant header
        String privateTenant = req.getHeader(PRIVATE_TENANT_HEADER);

        if (privateTenant != null) {
            TenantContext.setCurrentTenant(privateTenant); // Use the header to set the tenant
        } 
//        else  {
//        	TenantContext.setCurrentTenant("public");
//        }
        
        chain.doFilter(request, response);
    }
}