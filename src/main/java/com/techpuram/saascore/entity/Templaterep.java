package com.techpuram.saascore.entity;

import org.springframework.data.jpa.repository.JpaRepository;




public interface Templaterep extends JpaRepository<Template, Long>{
    Template findByTemplateName(String templateName);

}
