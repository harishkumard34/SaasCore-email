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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profiles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(
        name = "record_seq_generator",
        sequenceName = "record_sequence",
        allocationSize = 1
    )
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "profile_name", nullable = false, length = 255)
    private String profileName;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "created_time", nullable = false)
    @JsonSerialize(using = LongToDateSerializer.class)
    private Long createdTime;

    @Column(name = "modified_time")
    @JsonSerialize(using = LongToDateSerializer.class)
    private Long modifiedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id", insertable = false, updatable = false)
    @JsonSerialize(using = UserDetailSerializer.class)
    private Users createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by", referencedColumnName = "user_id", insertable = false, updatable = false)
    @JsonSerialize(using = UserDetailSerializer.class)
    private Users modifiedBy;
}
