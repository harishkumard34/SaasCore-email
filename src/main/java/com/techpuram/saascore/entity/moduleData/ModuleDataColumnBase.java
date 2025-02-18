package com.techpuram.saascore.entity.moduleData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ModuleDataColumnBase {
    @Id
    private Long id;

    @Column(name = "string_column1")
    private String stringColumn1;

    @Column(name = "string_column2")
    private String stringColumn2;

    @Column(name = "string_column3")
    private String stringColumn3;

    @Column(name = "string_column4")
    private String stringColumn4;

    @Column(name = "string_column5")
    private String stringColumn5;

    @Column(name = "string_column6")
    private String stringColumn6;

    @Column(name = "string_column7")
    private String stringColumn7;

    @Column(name = "string_column8")
    private String stringColumn8;

    @Column(name = "string_column9")
    private String stringColumn9;

    @Column(name = "string_column10")
    private String stringColumn10;

    @Column(name = "string_column11")
    private String stringColumn11;

    @Column(name = "string_column12")
    private String stringColumn12;

    @Column(name = "string_column13")
    private String stringColumn13;

    @Column(name = "string_column14")
    private String stringColumn14;

    @Column(name = "string_column15")
    private String stringColumn15;

    @Column(name = "string_column16")
    private String stringColumn16;

    @Column(name = "string_column17")
    private String stringColumn17;

    @Column(name = "string_column18")
    private String stringColumn18;

    @Column(name = "string_column19")
    private String stringColumn19;

    @Column(name = "string_column20")
    private String stringColumn20;

    @Column(name = "string_column21")
    private String stringColumn21;

    @Column(name = "string_column22")
    private String stringColumn22;

    @Column(name = "string_column23")
    private String stringColumn23;

    @Column(name = "string_column24")
    private String stringColumn24;

    @Column(name = "string_column25")
    private String stringColumn25;

    @Column(name = "string_column26")
    private String stringColumn26;

    @Column(name = "string_column27")
    private String stringColumn27;

    @Column(name = "string_column28")
    private String stringColumn28;

    @Column(name = "string_column29")
    private String stringColumn29;

    @Column(name = "string_column30")
    private String stringColumn30;

    @Column(name = "string_column31")
    private String stringColumn31;

    @Column(name = "string_column32")
    private String stringColumn32;

    @Column(name = "string_column33")
    private String stringColumn33;

    @Column(name = "string_column34")
    private String stringColumn34;

    @Column(name = "string_column35")
    private String stringColumn35;

    @Column(name = "string_column36")
    private String stringColumn36;

    @Column(name = "string_column37")
    private String stringColumn37;

    @Column(name = "string_column38")
    private String stringColumn38;

    @Column(name = "string_column39")
    private String stringColumn39;

    @Column(name = "string_column40")
    private String stringColumn40;

    @Column(name = "int_column1")
    private Integer intColumn1;

    @Column(name = "int_column2")
    private Integer intColumn2;

    @Column(name = "int_column3")
    private Integer intColumn3;

    @Column(name = "int_column4")
    private Integer intColumn4;

    @Column(name = "int_column5")
    private Integer intColumn5;

    @Column(name = "int_column6")
    private Integer intColumn6;

    @Column(name = "int_column7")
    private Integer intColumn7;

    @Column(name = "int_column8")
    private Integer intColumn8;

    @Column(name = "int_column9")
    private Integer intColumn9;

    @Column(name = "int_column10")
    private Integer intColumn10;

    @Column(name = "int_column11")
    private Integer intColumn11;

    @Column(name = "int_column12")
    private Integer intColumn12;

    @Column(name = "int_column13")
    private Integer intColumn13;

    @Column(name = "int_column14")
    private Integer intColumn14;

    @Column(name = "int_column15")
    private Integer intColumn15;

    @Column(name = "int_column16")
    private Integer intColumn16;

    @Column(name = "int_column17")
    private Integer intColumn17;

    @Column(name = "int_column18")
    private Integer intColumn18;

    @Column(name = "int_column19")
    private Integer intColumn19;

    @Column(name = "int_column20")
    private Integer intColumn20;

    @Column(name = "int_column21")
    private Integer intColumn21;

    @Column(name = "int_column22")
    private Integer intColumn22;

    @Column(name = "int_column23")
    private Integer intColumn23;

    @Column(name = "int_column24")
    private Integer intColumn24;

    @Column(name = "int_column25")
    private Integer intColumn25;

    @Column(name = "boolean_column1")
    private Boolean booleanColumn1;

    @Column(name = "boolean_column2")
    private Boolean booleanColumn2;

    @Column(name = "boolean_column3")
    private Boolean booleanColumn3;

    @Column(name = "boolean_column4")
    private Boolean booleanColumn4;

    @Column(name = "boolean_column5")
    private Boolean booleanColumn5;

    @Column(name = "boolean_column6")
    private Boolean booleanColumn6;

    @Column(name = "boolean_column7")
    private Boolean booleanColumn7;

    @Column(name = "boolean_column8")
    private Boolean booleanColumn8;

    @Column(name = "boolean_column9")
    private Boolean booleanColumn9;

    @Column(name = "boolean_column10")
    private Boolean booleanColumn10;

    @Column(name = "text_column1")
    private String textColumn1;

    @Column(name = "text_column2")
    private String textColumn2;

    @Column(name = "text_column3")
    private String textColumn3;

    @Column(name = "text_column4")
    private String textColumn4;

    @Column(name = "text_column5")
    private String textColumn5;

    public void setMainTable(ModuleDataBase mainTable) {

    }
}