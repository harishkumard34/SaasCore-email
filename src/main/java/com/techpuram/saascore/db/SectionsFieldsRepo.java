package com.techpuram.saascore.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techpuram.saascore.entity.modules.SectionsFields;

public interface SectionsFieldsRepo extends JpaRepository<SectionsFields, Long> {

	@Query("SELECT sf FROM SectionsFields sf JOIN FETCH sf.section s JOIN FETCH sf.field f WHERE s.sectionId = :sectionId")
    List<SectionsFields> findAllBySectionId(@Param("sectionId") Long sectionId);


    @Query("SELECT sf FROM SectionsFields sf JOIN FETCH sf.section s JOIN FETCH sf.field f JOIN FETCH sf.module m WHERE m.moduleId = :moduleId")
    List<SectionsFields> findAllByModuleId(@Param("moduleId") Long moduleId);

}
