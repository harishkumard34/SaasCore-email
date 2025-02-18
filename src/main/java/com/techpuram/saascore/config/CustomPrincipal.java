package com.techpuram.saascore.config;

public class CustomPrincipal {
    private Long userId;
    private String email;
    private String tenant;

    public CustomPrincipal(Long userId, String email, String tenant) {
        this.userId = userId;
        this.email = email;
        this.tenant = tenant;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getTenant() {
        return tenant;
    }
}

