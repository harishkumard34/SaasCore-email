package com.techpuram.saascore.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techpuram.saascore.entity.Tenants;

@Repository
public interface TenantRepository extends JpaRepository<Tenants, Long> {
    Optional<Tenants> findBySchemaName(String schemaName);


    @Query("SELECT t.schemaName FROM Tenants t WHERE t.tenantId = :tenantId")
    String findSchemaByTenantId(@Param("tenantId") Long tenantId);

}