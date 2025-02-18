package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpuram.saascore.entity.Company;

/**
 * Repository is an interface that provides access to data in a database
 */
public interface CompanyRepo extends JpaRepository<Company, Long> {
}