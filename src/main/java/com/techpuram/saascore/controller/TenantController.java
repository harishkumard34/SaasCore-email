package com.techpuram.saascore.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpuram.saascore.config.TenantContext;

@RestController
@RequestMapping("/tenants")
public class TenantController {

    @Autowired
    private DataSource dataSource;

    @PostMapping("/create")
    public String createTenant(@RequestParam("id") String tenantId) {
        try (Connection connection = dataSource.getConnection()) {
            connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS " + tenantId);
            TenantContext.setCurrentTenant(tenantId);

            // Run Flyway migrations for the new tenant schema
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .schemas(tenantId)
                    .locations("classpath:/db/migration")
                    .baselineOnMigrate(true)
                    .load();
            flyway.migrate();

            return "Tenant schema created and initialized: " + tenantId;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create schema for tenant: " + tenantId, e);
        } finally {
        	TenantContext.clear();
        }
    }
}
