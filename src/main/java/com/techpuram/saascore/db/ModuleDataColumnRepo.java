package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techpuram.saascore.entity.moduleData.ModuleDataColumnBase;

@Repository
public interface ModuleDataColumnRepo extends JpaRepository<ModuleDataColumnBase, Long> {
}
