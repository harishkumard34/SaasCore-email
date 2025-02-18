package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpuram.saascore.entity.modules.Layouts;

public interface LayoutsRepo extends JpaRepository<Layouts, Long> {

}