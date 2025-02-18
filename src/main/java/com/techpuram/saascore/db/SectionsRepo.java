package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpuram.saascore.entity.modules.Sections;

public interface SectionsRepo extends JpaRepository<Sections, Long> {

}