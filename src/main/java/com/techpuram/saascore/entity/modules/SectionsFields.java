package com.techpuram.saascore.entity.modules;

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
@Table(name = "sections_fields")
public class SectionsFields {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(
            name = "record_seq_generator",
            sequenceName = "record_sequence",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Sections section; // Reference to the Sections entity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Fields field; // Reference to the Fields entity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Modules module; // Reference to the Modules entity

    @Column(name = "column_name")
    private String columnName; // The actual column name in the module data table

    @Column(name = "mandatory")
    private Boolean mandatory; // Indicates if the field is mandatory

    @Column(name = "field_order")
    private Integer fieldOrder; // Specifies the order of the field in the section

    @Column(name = "unique_field")
    private Boolean uniqueField; // Indicates if the field value must be unique

    @Column(name = "hippa")
    private Boolean hippa; // Indicates if the field is HIPAA compliant

    @Column(name = "phi_field")
    private Boolean phiField; // Indicates if the field is PHI (Protected Health Information)
}
