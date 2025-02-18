package com.techpuram.saascore.entity.moduleData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "module_data_3") 
@Data
public class ModuleData3 extends ModuleDataBase {
    
    @OneToOne(mappedBy = "moduleData", cascade = CascadeType.ALL, orphanRemoval = true)
    private ModuleDataColumn3 moduleDataColumn;
}
