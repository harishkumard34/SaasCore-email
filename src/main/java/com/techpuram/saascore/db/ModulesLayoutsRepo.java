package com.techpuram.saascore.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techpuram.saascore.entity.modules.ModulesLayouts;

public interface ModulesLayoutsRepo extends JpaRepository<ModulesLayouts, Long> {
    
    @Query("SELECT ml FROM ModulesLayouts ml JOIN FETCH ml.module m JOIN FETCH ml.layout l WHERE m.moduleId = :moduleId")
    List<ModulesLayouts> findAllByModuleId(@Param("moduleId") Long moduleId);
    
}
