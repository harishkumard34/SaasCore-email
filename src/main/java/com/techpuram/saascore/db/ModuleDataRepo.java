package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techpuram.saascore.entity.moduleData.ModuleDataBase;

@Repository
public interface ModuleDataRepo extends JpaRepository<ModuleDataBase, Long> {
}
