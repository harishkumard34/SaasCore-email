package com.techpuram.saascore.entity.modules;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techpuram.saascore.serializer.LongToDateSerializer;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "picklist_options")
public class PicklistOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(name = "record_seq_generator", sequenceName = "record_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Fields field; // Reference to the field

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Modules module; // Reference to the module

    @Column(name = "option_value")
    private String optionValue; // Actual value

    @Column(name = "display_label")
    private String displayLabel; // Label for the picklist option

    @Column(name = "is_default")
    private Boolean isDefault; // Indicates if this is the default value

    @Column(name = "is_active")
    private Boolean isActive = true; // Indicates if the option is active

    @Column(name = "option_order")
    private Integer optionOrder; // Order of the options

    @Column(name = "created_time")
    @JsonSerialize(using = LongToDateSerializer.class)
    private Long createdTime;

    @Column(name = "modified_time")
    @JsonSerialize(using = LongToDateSerializer.class)
    private Long modifiedTime;
}

