package com.techpuram.saascore.entity;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techpuram.saascore.serializer.LongToDateSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * An entity class represents a table in a relational database
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "auth", schema = "public")
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="auth_id")
    private Integer authId;
    
    @Column(name="auth_name", nullable = false, length = 150)
    private String authName;
    
    @Column(name="email", nullable = false, unique = true, length = 200)
    private String email;
    
    @Column(name="phone", nullable = false, unique = true, length = 20)
    private String phone;
    
    @Column(name="password_phrase", nullable = false, length = 2000)
    private String passwordPhrase;
    
    @Column(name="tenant_id", nullable = false)
    private Long tenantId;

    @Column(name="user_id")
    private Long userId;
    
    @Column(name="last_logintime")
    private Long lastLogintime;
    
    @Column(name="created_time")
    @JsonSerialize(using = LongToDateSerializer.class)  
    private Long createdTime;
    
    @Column(name="session_id")
    private String sessionId;
    
    @Column(name="modified_time")
    @JsonSerialize(using = LongToDateSerializer.class)
    private Long modifiedTime;
    
    @Column(name="previous_passwd")
    private String previousPasswd;
    
    @Column(name="properties")
    private String properties;
    
    @Column(name="login_ip")
    private String loginIp;
    
    @Column(name="login_agent")
    private String loginAgent;
    
    @Column(name="failed_count",columnDefinition = "INTEGER DEFAULT 0")
    private Integer failedCount;
    
    @Column(name="reset_request")
    private Boolean resetRequest;
    
    @Column(name="otp", nullable = false, length = 6)
    private String otp;
    

}
