package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techpuram.saascore.entity.RoleHierarchy;

@Repository
public interface RoleHierarchyRepo extends JpaRepository<RoleHierarchy, Long> {
}