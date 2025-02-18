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

// enum LayoutType {
//     CREATE,
//     DISPLAY;
// }

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "layouts")
public class Layouts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq_generator")
    @SequenceGenerator(name = "record_seq_generator", // Generator name
            sequenceName = "record_sequence", // Name of the database sequence
            allocationSize = 1 // Number of IDs to prefetch (set to 1 for no caching)
    )
    @Column(name = "layout_id")
    private Long layoutId;

    @Column(name = "layout_name")
    private String layoutName;

   

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

    @Column(name = "system_defined")
    private Boolean systemDefined;

    @Column(name = "metadata")
    private String metadata; 

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private LayoutType type;
}

enum LayoutType {
    CREATE(1),
    DISPLAY(2);

    private final int value;

    LayoutType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
