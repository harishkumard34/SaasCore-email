package com.techpuram.saascore.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techpuram.saascore.serializer.LongToDateSerializer;
import com.techpuram.saascore.serializer.ProfileSerializer;
import com.techpuram.saascore.serializer.RoleSerializer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(
        name = "record_seq_generator", 
        sequenceName = "record_sequence", 
        allocationSize = 1
    )
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "role_id", nullable = true)
    @JsonSerialize(using = RoleSerializer.class)
    private Roles role;

    @Column(name = "status")
    private String status;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "dob")
    private LocalDateTime dob;

    @JsonSerialize(using = LongToDateSerializer.class)
    @Column(name = "created_time", nullable = false)
    private Long createdTime;

    @Column(name = "modified_time")
    private Long modifiedTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "profile", referencedColumnName = "profile_id", nullable = true)
    @JsonSerialize(using = ProfileSerializer.class)
    private Profiles profile;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "gender")
    private String gender;

    @Column(name = "time_zone")
    private LocalDateTime timeZone;

    @Column(name = "language")
    private String language;

    @Column(name = "locale")
    private String locale;
}
