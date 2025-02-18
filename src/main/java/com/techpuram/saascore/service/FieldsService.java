package com.techpuram.saascore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.FieldsRepo;
import com.techpuram.saascore.entity.modules.Fields;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service layer is where all the business logic lies
 */
@Service
@RequiredArgsConstructor
@Slf4j

public class FieldsService {
    private final FieldsRepo FieldsRepo;

    public List<Fields> getAllFields() {

        log.info("All fields are fetched");
        return FieldsRepo.findAll();
    }

    public Fields getFieldsByID(Long id) {
        Fields fields = FieldsRepo.findById(id).orElse(null);
        if (fields == null) {
            throw new RuntimeException("Fields not found for id :: " + id);
        }

        return FieldsRepo.findById(id).orElse(null);
    }

    public Fields createFields(Fields fields) {
        fields.setCreatedTime(System.currentTimeMillis());
        fields.setModifiedTime(System.currentTimeMillis());

        // log.info("Fields with id {} is created", fields.getFieldId());
        return FieldsRepo.save(fields);
    }

    public List<Fields> createFields(List<Fields> fieldsList) {
        fieldsList.forEach(fields -> {
            fields.setCreatedTime(System.currentTimeMillis());
            fields.setModifiedTime(System.currentTimeMillis());
        });
        log.info("Creating multiple fields");
        return FieldsRepo.saveAll(fieldsList);
    }


    public List<Fields> updateFields(Fields fields) {
        fields.setModifiedTime(System.currentTimeMillis());

        log.info("Fields with id {} is updated", fields.getFieldId());
        return FieldsRepo.saveAll(List.of(fields));
    }

    public void deleteFields(Long id) {
        FieldsRepo.deleteById(id);

        log.info("Fields with id {} is deleted", id);
    }

    public void deleteAllFields() {
        FieldsRepo.deleteAll();

        log.info("All fields are deleted");
    }

    public Fields updateFieldLabel(Long id, String newLabel) {
        Fields field = FieldsRepo.findById(id).orElseThrow(() -> new RuntimeException("Field not found for id :: " + id));
        field.setDisplayName(newLabel);
        field.setModifiedTime(System.currentTimeMillis());
        return FieldsRepo.save(field);
    }
}

