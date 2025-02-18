package com.techpuram.saascore.entity.moduleData;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "module_data_column_4") // ✅ Maps to `module_data_column_2` table
@Data
public class ModuleDataColumn4 extends ModuleDataColumnBase {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private ModuleData4 moduleData;

    public void setMainTable(ModuleData4 moduleData) {
        this.moduleData = moduleData;
    }
}
