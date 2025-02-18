package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpuram.saascore.entity.modules.Fields;

/**
 * Repository is an interface that provides access to data in a database
 */
public interface FieldsRepo extends JpaRepository<Fields, Long> {
}