package com.techpuram.saascore.entity.modules;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techpuram.saascore.entity.Users;
import com.techpuram.saascore.serializer.LongToDateSerializer;
import com.techpuram.saascore.serializer.UserDetailSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "fields")
public class Fields {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(name = "record_seq_generator", sequenceName = "record_sequence", allocationSize = 1)
    @Column(name = "field_Id")
    private Long fieldId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "column_name")
    private String columnName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private FieldsType type;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "min_length")
    private Integer minLength;

    @Column(name = "default_value")
    private String defaultValue; // Default value for the field

    @Column(name = "validation_rule")
    private String validationRule; // Validation rules for fields (e.g., regex, min/max constraints)

    @Column(name = "file_type")
    private String fileType; // Allowed file types/extensions for FILE/IMAGE fields

    @Column(name = "is_picklist")
    private Boolean isPicklist;

    @Column(name = "storage_path")
    private String storagePath; // Storage location for FILE/IMAGE fields

    @Column(name = "lookup_table")
    private String lookupTable; // Reference for LOOKUP fields

    @Column(name = "display_name")
    private String displayName;

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


// Enum for the `type` field

enum FieldsType {
    SINGLELINE(0),
    MULTILINE(1),
    PHONE(2),
    EMAIL(3),
    PICKLIST(4),
    RADIO(5),
    CHECKBOX(6),
    DATE(7),
    DATETIME(8),
    TIME(9),
    NUMBER(10),
    CURRENCY(11),
    PERCENTAGE(12),
    URL(13),
    FILE(14),
    IMAGE(15),
    LOOKUP(16),
    GEOLOCATION(17),
    AUTONUMBER(18);

    private final int value;

    FieldsType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

