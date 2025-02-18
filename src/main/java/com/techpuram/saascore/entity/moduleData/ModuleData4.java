package com.techpuram.saascore.entity.moduleData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "module_data_4") 
@Data
public class ModuleData4 extends ModuleDataBase {
    
    @OneToOne(mappedBy = "moduleData", cascade = CascadeType.ALL, orphanRemoval = true)
    private ModuleDataColumn4 moduleDataColumn;
}
