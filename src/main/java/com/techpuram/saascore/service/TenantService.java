package com.techpuram.saascore.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.TenantRepository;
import com.techpuram.saascore.entity.Tenants;

import jakarta.transaction.Transactional;

@Service
public class TenantService {

	 @Autowired
	    private TenantRepository tenantRepository;

	    @Autowired
	    private DataSource dataSource;

	    private static final long SEQUENCE_INCREMENT = 100000000L; //10Cr records
	    
		String tenantName = "";
	    
	    @Transactional
	    public Long registerTenant() {
	        // Generate a unique schema name for the tenant

		 
	     // Get the next sequence start value
	        long sequence = getNextSequenceStart();
			long sequenceStart = sequence * SEQUENCE_INCREMENT;
	        long sequenceEnd = sequenceStart + SEQUENCE_INCREMENT -1;
			tenantName = "saasdb_" + sequence;
	        
	        // Save tenant details in the tenants table
	        Tenants tenant = new Tenants();
	        tenant.setSchemaName(tenantName);
	        tenant.setSequenceStart(sequenceStart);
	        tenant.setSequenceEnd(sequenceEnd);
	        tenant.setActive(true);
	        tenant.setCreatedTime(System.currentTimeMillis());
	        tenantRepository.save(tenant);

	        // Create the schema and run Flyway migrations
	        try (Connection connection = dataSource.getConnection()) {
	            Statement stmt = connection.createStatement();

	            // Create schema for the tenant
	            stmt.execute(String.format("CREATE SCHEMA IF NOT EXISTS %s", tenantName));

	            // Create sequence for the tenant
	            stmt.execute(String.format(
	                "CREATE SEQUENCE IF NOT EXISTS %s.record_sequence START %d INCREMENT BY 1",
	                tenantName, sequenceStart
	            ));

	            // Run tenant-specific Flyway migrations
	            Flyway flyway = Flyway.configure()
	                    .dataSource(dataSource)
	                    .schemas(tenantName)
						.placeholders(Map.of("schema", tenantName))  
	                    .locations("classpath:/db/migration/tenant") // Separate folder for tenant migrations
	                    .baselineOnMigrate(true)
	                    .load();
	            flyway.migrate();
	        } catch (SQLException e) {
	            throw new RuntimeException("Error creating schema, sequence, or running migrations: " + tenantName, e);
	        }
	        updateSequenceTracker(sequence+1);
	        return tenant.getTenantId();
	    }
	    
	    
		public String getTenantName() { return tenantName; }

		public String getTenantName(Long id) {   return tenantRepository.findSchemaByTenantId(id); }

	    private long getNextSequenceStart() {
	        // Fetch the last used sequence from the tracker
	        try (Connection connection = dataSource.getConnection()) {
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT last_sequence FROM public.tenant_sequence_tracker ORDER BY id DESC LIMIT 1");
	            if (rs.next()) {
	                long lastSequence = rs.getLong("last_sequence");
	                return lastSequence;
	            } else {
	                // If no records exist, return the initial starting value
					return 10000L; // 15-digit value
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException("Error fetching the last sequence from tracker", e);
	        }
	    }

	private void updateSequenceTracker(long newSequenceStart) {
	        // Update the tracker with the new sequence start for the next tenant
	        try (Connection connection = dataSource.getConnection()) {
	            PreparedStatement stmt = connection.prepareStatement("INSERT INTO public.tenant_sequence_tracker (last_sequence) VALUES (?)");
	            stmt.setLong(1, newSequenceStart);
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            throw new RuntimeException("Error updating sequence tracker", e);
	        }
	    }
	}