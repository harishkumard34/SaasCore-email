package com.techpuram.saascore.entity.moduleData;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "module_data_column_5") // ✅ Maps to `module_data_column_2` table
@Data
public class ModuleDataColumn5 extends ModuleDataColumnBase {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private ModuleData5 moduleData;

    public void setMainTable(ModuleData5 moduleData) {
        this.moduleData = moduleData;
    }
}
