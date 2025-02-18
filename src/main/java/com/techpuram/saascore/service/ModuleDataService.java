package com.techpuram.saascore.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techpuram.saascore.config.RequestContext;
import com.techpuram.saascore.db.ModuleData1Repo;
import com.techpuram.saascore.db.ModuleDataColumn1Repo;
import com.techpuram.saascore.db.ModulesRepo;
import com.techpuram.saascore.entity.Users;
import com.techpuram.saascore.entity.moduleData.ModuleData1;
import com.techpuram.saascore.entity.moduleData.ModuleDataColumn1;
import com.techpuram.saascore.entity.modules.Fields;
import com.techpuram.saascore.entity.modules.Modules;
import com.techpuram.saascore.serializer.LongToDateSerializer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleDataService {

    private final ModuleData1Repo moduleDataRepo;
    private final ModuleDataColumn1Repo moduleDataColumnRepo;
    private final ModulesService modulesService;
    private final ModulesRepo modulesRepo;
    private final SectionsService sectionsService;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Checks if a module exists in the database.
     */
    public boolean isModuleExists(String moduleName) {
        return modulesRepo.existsByModuleName(moduleName);
    }

   @Transactional
    public Long insertModuleData(String moduleName, Map<String, Object> requestData) {
        // ✅ Check if module exists
        if (!isModuleExists(moduleName)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found");
        }

        Long moduleId = modulesRepo.findByModuleName(moduleName)
                                   .map(Modules::getModuleId)
                                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found"));

        Users currentUser = RequestContext.getCurrentUser();
    
        
        // ✅ Create moduleData instance
        ModuleData1 moduleData = new ModuleData1();
        moduleData.setModuleId(moduleId);
        moduleData.setCreatedTime(System.currentTimeMillis());
        moduleData.setModifiedTime(System.currentTimeMillis());
        moduleData.setCreatedBy(currentUser); // ✅ Set created_by
        moduleData.setModifiedBy(currentUser); // ✅ Set modified_by
        moduleData.setOwner(currentUser); // ✅ Set owner
        moduleData = moduleDataRepo.save(moduleData); // ✅ Persist Parent First

        // ✅ Create and save ModuleDataColumn1 dynamically
        ModuleDataColumn1 moduleDataColumn = new ModuleDataColumn1();


        List<Fields> fieldsList = sectionsService.getAllFieldsByModuleId(moduleId);

        for (Fields field : fieldsList) {
            if (requestData.containsKey(field.getFieldName())) {
                String columnName = field.getColumnName();
                Object value = requestData.get(field.getFieldName());
                setColumnValue(moduleDataColumn, columnName, value);
            }
        }
       
        moduleDataColumn.setId(moduleData.getId()); // ✅ Set ID to match ModuleData1 ID
        moduleDataColumn.setMainTable(moduleData); // ✅ Link to Parent
        entityManager.persist(moduleDataColumn); // ✅ Save linked entity
        entityManager.flush();

        return moduleData.getId();
    }

    /**
     * Retrieves all records for a module.
     */
    public List<Map<String, Object>> getAllModuleData(String moduleName) {
        if (!isModuleExists(moduleName)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found");
        }

        List<ModuleData1> moduleDataList = moduleDataRepo.findAll();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (ModuleData1 moduleData : moduleDataList) {
            Map<String, Object> record = getModuleDataById(moduleName, moduleData.getId());
            if (!record.isEmpty()) {
                responseList.add(record);
            }
        }
        return responseList;
    }

    /**
     * Retrieves a single record by ID.
     */
    public Map<String, Object> getModuleDataById(String moduleName, Long id) {
        if (!isModuleExists(moduleName)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found");
        }

        ModuleData1 moduleData = moduleDataRepo.findById(id).orElse(null);
        if (moduleData == null) {
            return Collections.emptyMap();
        }

        ModuleDataColumn1 moduleDataColumn = moduleDataColumnRepo.findById(id).orElse(null);
        if (moduleDataColumn == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", moduleData.getId());
        response.put("module_id", moduleData.getModuleId());
        response.put("created_time", LongToDateSerializer.format(moduleData.getCreatedTime()));
        response.put("modified_time", LongToDateSerializer.format(moduleData.getModifiedTime()));
        response.put("created_by", moduleData.getCreatedBy());
        response.put("modified_by", moduleData.getModifiedBy());
        response.put("owner", moduleData.getOwner());

        List<Fields> fieldsList = sectionsService.getAllFieldsByModuleId(moduleData.getModuleId());

        for (Fields field : fieldsList) {
            String columnName = field.getColumnName();
            response.put(field.getFieldName(), getColumnValue(moduleDataColumn, columnName));
        }

        return response;
    }

    private Object getColumnValue(ModuleDataColumn1 moduleDataColumn, String columnName) {
        try {
            var field = moduleDataColumn.getClass().getDeclaredField(columnName);
            field.setAccessible(true);
            return field.get(moduleDataColumn);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Sets column value dynamically.
     */
    private void setColumnValue(ModuleDataColumn1 moduleDataColumn, String columnName, Object value) {
        try {
            Field field = getFieldFromHierarchy(moduleDataColumn.getClass(), columnName);
            System.out.println("✅ Setting value for: " + columnName);

            if (field.getType() == Integer.class) {
                field.set(moduleDataColumn, Integer.parseInt(value.toString()));
            } else if (field.getType() == Long.class) {
                field.set(moduleDataColumn, Long.parseLong(value.toString()));
            } else if (field.getType() == String.class) {
                field.set(moduleDataColumn, value.toString());
            } else {
                throw new RuntimeException("Unsupported field type: " + field.getType());
            }
        } catch (IllegalAccessException | NumberFormatException e) {
            throw new RuntimeException("Error setting field value: " + columnName, e);
        }
    }

    private static Field getFieldFromHierarchy(Class<?> clazz, String fieldName) {
        while (clazz != null) { // ✅ Loop through all parent classes
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // ✅ Move to parent class
            }
        }
        return null;
    }
    
    // ✅ Usage:
   
    
}
