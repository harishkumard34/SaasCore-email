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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "profile_permissions")
public class ProfilePermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(
        name = "record_seq_generator",          // Generator name
        sequenceName = "record_sequence",       // Name of the shared database sequence
        allocationSize = 1                      // Fetch one ID at a time
    )
    @Column(name = "profile_permission_id")
    private Long profilePermissionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profiles profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permissions_id", nullable = false)
    private Permissions permissions;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_time", nullable = false)
    @JsonSerialize(using = LongToDateSerializer.class)
    private Long createdTime;

    @Column(name = "modified_time")
    @JsonSerialize(using = LongToDateSerializer.class)
    private Long modifiedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by", referencedColumnName = "user_id")
    @JsonSerialize(using = UserDetailSerializer.class)
    private Users modifiedBy;
}
