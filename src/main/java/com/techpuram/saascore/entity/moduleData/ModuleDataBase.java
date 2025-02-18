package com.techpuram.saascore.entity.moduleData;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techpuram.saascore.entity.Users;
import com.techpuram.saascore.serializer.LongToDateSerializer;
import com.techpuram.saascore.serializer.UserDetailSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class ModuleDataBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(name = "record_seq_generator", sequenceName = "record_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "module_id", nullable = false)
    private Long moduleId;

    @JsonSerialize(using = LongToDateSerializer.class) // ✅ Reuse Serializer
    @Column(name = "created_time", updatable = false)
    private Long createdTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    @Column(name = "modified_time")
    private Long modifiedTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id", nullable = false)
    @JsonSerialize(using = UserDetailSerializer.class)
    private Users createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modified_by", referencedColumnName = "user_id", nullable = false)
    @JsonSerialize(using = UserDetailSerializer.class)
    private Users modifiedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", referencedColumnName = "user_id", nullable = false)
    @JsonSerialize(using = UserDetailSerializer.class)
    private Users owner;

}
