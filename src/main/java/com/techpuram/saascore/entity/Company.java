package com.techpuram.saascore.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techpuram.saascore.serializer.LongToDateSerializer;
import com.techpuram.saascore.serializer.UserDetailSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An entity class represents a table in a relational database.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(
        name = "record_seq_generator",          // Generator name
        sequenceName = "record_sequence",       // Shared database sequence
        allocationSize = 1                      // Fetch one ID at a time
    )
    @Column(name = "org_id")
    private Long orgId;

    @Column(name = "org_name", nullable = false, length = 250)
    private String orgName;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "street", length = 255)
    private String street;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "pincode")
    private Integer pincode;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "website", length = 255)
    private String website;

    @Column(name = "created_time", nullable = false)
    @JsonSerialize(using = LongToDateSerializer.class)
    private Long createdTime;

    @Column(name = "modified_time")
    @JsonSerialize(using = LongToDateSerializer.class)
    private Long modifiedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by", referencedColumnName = "user_id", insertable = false, updatable = false)
    @JsonSerialize(using = UserDetailSerializer.class)
    private Users modifiedBy;

    @Column(name = "gdpr_compliance", length = 1000)
    private String gdprCompliance;

    @Column(name = "hipaa_compliance", length = 1000)
    private String hipaaCompliance;

    @Column(name = "properties", length = 1000)
    private String properties;
}
