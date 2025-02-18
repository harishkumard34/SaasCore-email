package com.techpuram.saascore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.techpuram.saascore.service.ModuleDataService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/{moduleName}/data")
@RequiredArgsConstructor
@Validated
public class ModuleDataController {

    private final ModuleDataService moduleDataService;

    /**
     * Inserts new data for a module.
     */
    @PostMapping
    public ResponseEntity<String> postModuleData(@PathVariable(name = "moduleName") String moduleName,
                                                 @RequestBody Map<String, Object> entityData) {
        Long recordId = moduleDataService.insertModuleData(moduleName, entityData);
        return ResponseEntity.ok("Data added successfully with ID: " + recordId);
    }

    /**
     * Retrieves all records for a module.
     * Returns 204 No Content if no records are found.
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllModuleData(@PathVariable(name = "moduleName") String moduleName) {
        List<Map<String, Object>> records = moduleDataService.getAllModuleData(moduleName);
        if (records.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // ✅ 204 No Content
        }
        return ResponseEntity.ok(records);
    }

    /**
     * Retrieves a single record by ID.
     * Returns 204 No Content if the record does not exist.
     * Returns 404 Not Found if the module is invalid.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getModuleDataById(@PathVariable(name = "moduleName") String moduleName,
                                                                 @PathVariable(name = "id") Long id) {
        if (!moduleDataService.isModuleExists(moduleName)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Module not found"));
        }

        Map<String, Object> record = moduleDataService.getModuleDataById(moduleName, id);
        if (record == null || record.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // ✅ 204 No Content
        }

        return ResponseEntity.ok(record);
    }
}
