package com.techpuram.saascore.config;

import java.util.Objects;

import org.slf4j.MDC;

public class TenantContext {
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();
    private static final String LOGGER_TENANT_ID = "tenant_id";
    private static final String DEFAULT_TENANT = "public";
    
    public static String getCurrentTenant() {
        String tenant = currentTenant.get();
        return Objects.requireNonNullElse(tenant, DEFAULT_TENANT);
    }

    public static void setCurrentTenant(String tenant) {
        if (tenant == null || tenant.isEmpty()) {
            throw new IllegalArgumentException("Tenant cannot be null or empty");
        }
        MDC.put(LOGGER_TENANT_ID, tenant);
        currentTenant.set(tenant);
    }

    public static void clear() {
    	MDC.clear();
        currentTenant.remove();
    }
}
