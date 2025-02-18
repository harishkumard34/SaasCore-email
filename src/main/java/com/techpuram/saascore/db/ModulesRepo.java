package com.techpuram.saascore.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpuram.saascore.entity.modules.Modules;

public interface ModulesRepo extends JpaRepository<Modules, Long> {
    
    /**
     * Checks if a module with the given name exists.
     */
    boolean existsByModuleName(String moduleName);

    /**
     * Finds a module by its name.
     */
    Optional<Modules> findByModuleName(String moduleName);
}
