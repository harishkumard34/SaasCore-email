package com.techpuram.saascore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tenants", schema = "public")
public class Tenants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "org_name",length = 255)
    private String orgName;

    @Column(name = "schema_name", nullable = false, unique = true, length = 255)
    private String schemaName;

    @Column(name = "sequence_start", nullable = false)
    private Long sequenceStart;

    @Column(name = "sequence_end", nullable = false)
    private Long sequenceEnd;

    @Column(name = "created_time", nullable = false)
    private Long createdTime;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}


