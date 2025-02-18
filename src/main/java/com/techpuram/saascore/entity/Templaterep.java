package com.techpuram.saascore.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpuram.saascore.entity.Template;


public interface Templaterep extends JpaRepository<Template, Long>{
    Template findByTemplateName(String templateName);

}
