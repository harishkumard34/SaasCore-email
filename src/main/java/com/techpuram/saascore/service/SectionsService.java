package com.techpuram.saascore.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.SectionsFieldsRepo;
import com.techpuram.saascore.db.SectionsRepo;
import com.techpuram.saascore.entity.modules.Fields;
import com.techpuram.saascore.entity.modules.Sections;
import com.techpuram.saascore.entity.modules.SectionsFields;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionsService {

    private final SectionsRepo sectionRepo;
    private final SectionsFieldsRepo sectionFieldRepo;

    public List<Sections> getAllSections() {
        log.info("All sections are fetched");

        return sectionRepo.findAll();
    }

    public Map<String, Object> getSectionWithFields(Long sectionId) {
        List<SectionsFields> sectionFields = sectionFieldRepo.findAllBySectionId(sectionId);

        if (sectionFields.isEmpty()) {
            throw new RuntimeException("No fields found for section id " + sectionId);
        }
        // Prepare the fields list
        List<Map<String, Object>> fields = sectionFields.stream().map(sf -> {
            Map<String, Object> fieldDetails = new LinkedHashMap<>();
            Fields field = sf.getField();
            // Extract field details
            fieldDetails.put("field_id", field.getFieldId());
            fieldDetails.put("field_name", field.getFieldName());
            fieldDetails.put("display_name", field.getDisplayName());
            // fieldDetails.put("column_name", field.getColumnName());
            fieldDetails.put("type", field.getType());
            fieldDetails.put("max_length", field.getMaxLength());
            fieldDetails.put("min_length", field.getMinLength());
            fieldDetails.put("created_time", field.getCreatedTime());
            fieldDetails.put("modified_time", field.getModifiedTime());
            fieldDetails.put("created_by", field.getCreatedBy());
            fieldDetails.put("modified_by", field.getModifiedBy());

            // Extracting section field details
            Map<String, Object> fieldMap = new LinkedHashMap<>();
            fieldMap.put("field_details", fieldDetails);
            fieldMap.put("mandatory", sf.getMandatory());
            fieldMap.put("order", sf.getFieldOrder());
            fieldMap.put("column_name", sf.getColumnName());
            fieldMap.put("unique_field", sf.getUniqueField());
            fieldMap.put("hippa", sf.getHippa());
            fieldMap.put("phi_field", sf.getPhiField());
            return fieldMap;
        }).collect(Collectors.toList());

        // Extract the section name from the first record
        Sections section = sectionFields.get(0).getSection();
        // Construct the response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("section_id", section.getSectionId());
        response.put("section_name", section.getSectionName());
        response.put("created_time", section.getCreatedTime());
        response.put("modified_time", section.getModifiedTime());
        response.put("created_by", section.getCreatedBy());
        response.put("modified_by", section.getModifiedBy());
        response.put("fields", fields);

        return response;
    }

    public Sections getSectionById(Long id) {
        log.info("Section with id {} is fetched", id);
        return sectionRepo.findById(id).orElse(null);
    }

    public List<Fields> getAllFieldsByModuleId(Long moduleId) {
        List<Fields> fieldsList = sectionFieldRepo.findAllByModuleId(moduleId).stream().map(sf -> sf.getField())
                .collect(Collectors.toList());
        log.info("fields with module ID {} is fetched", moduleId);
        return fieldsList;
    }
}
