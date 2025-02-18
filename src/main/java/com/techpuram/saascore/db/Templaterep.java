package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpuram.saascore.entity.Template;


public interface Templaterep extends JpaRepository<Template, Long>{
    Template findByTemplateName(String templateName);

}
