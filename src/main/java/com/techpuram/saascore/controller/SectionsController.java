package com.techpuram.saascore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techpuram.saascore.entity.modules.Fields;
import com.techpuram.saascore.entity.modules.Sections;
import com.techpuram.saascore.service.SectionsService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/sections")
public class SectionsController {

    @Autowired
    private final SectionsService sectionService;

    @GetMapping
    public ResponseEntity<List<Sections>> getSections() {
        // Test comment
        return ResponseEntity.ok().body(sectionService.getAllSections());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSectionWithFields(@PathVariable("id") Long sectionId) {
        Map<String, Object> response = sectionService.getSectionWithFields(sectionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/debug/{moduleId}")
    public ResponseEntity<List<Fields>> getAllFieldsByModuleId(@PathVariable("moduleId") Long moduleId) {
        return ResponseEntity.ok().body(sectionService.getAllFieldsByModuleId(moduleId));
    }


}
