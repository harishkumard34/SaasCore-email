package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techpuram.saascore.entity.moduleData.ModuleDataColumn1;

@Repository
public interface ModuleDataColumn1Repo extends JpaRepository<ModuleDataColumn1, Long> {
}
