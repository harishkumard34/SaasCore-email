package com.techpuram.saascore.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techpuram.saascore.entity.modules.LayoutsSections;

public interface LayoutSectionRepo extends JpaRepository<LayoutsSections, Long> {

    @Query("SELECT ls FROM LayoutsSections ls JOIN FETCH ls.layout l JOIN FETCH ls.section s WHERE l.layoutId = :layoutId")
    List<LayoutsSections> findAllByLayoutId(@Param("layoutId") Long layoutId);

}
