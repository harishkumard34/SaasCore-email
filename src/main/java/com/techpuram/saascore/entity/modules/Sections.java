package com.techpuram.saascore.entity.modules;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techpuram.saascore.entity.Users;
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
@Table(name = "sections")
public class Sections {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(name = "record_seq_generator", sequenceName = "record_sequence", allocationSize = 1)
    @Column(name = "section_id") 
    private Long sectionId;

    @Column(name = "section_name")
    private String sectionName;

    @Column(name = "visibility")
    private Boolean visibility;

    @Column(name = "created_time")
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

