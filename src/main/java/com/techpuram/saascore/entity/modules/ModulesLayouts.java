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
import lombok.Data;

@Data
@Entity
@Table(name = "modules_layouts")
public class ModulesLayouts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(name = "record_seq_generator", // Generator name
            sequenceName = "record_sequence", // Name of the database sequence
            allocationSize = 1 // Number of IDs to prefetch (set to 1 for no caching)
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Modules module;

    @ManyToOne
    @JoinColumn(name = "layout_id", nullable = false)
    private Layouts layout;

    @Column(name = "layout_order")
    private Integer layoutOrder;

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
