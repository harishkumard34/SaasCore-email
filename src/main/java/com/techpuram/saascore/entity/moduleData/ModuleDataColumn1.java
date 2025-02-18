package com.techpuram.saascore.entity.moduleData;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "module_data_column_1") 
@Data
public class ModuleDataColumn1 extends ModuleDataColumnBase {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private ModuleData1 moduleData;

    public void setMainTable(ModuleData1 moduleData) {
        this.moduleData = moduleData;
    }
}
