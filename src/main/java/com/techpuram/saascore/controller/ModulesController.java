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

import com.techpuram.saascore.entity.modules.Modules;
import com.techpuram.saascore.service.ModulesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/modules")
public class ModulesController {

    @Autowired
    private final ModulesService modulesService;

    @GetMapping
    public ResponseEntity<List<Modules>> getModules() {
        return ResponseEntity.ok().body(modulesService.getAllModules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getModuleWithLayouts(@PathVariable("id") Long moduleId) {
        return ResponseEntity.ok().body(modulesService.getModuleWithLayouts(moduleId));
    }

    // @GetMapping("/{moduleName}")
    public ResponseEntity<List<Modules>>
    getModulesByName(@PathVariable("moduleName") String moduleName) {
    return ResponseEntity.ok().body(modulesService.getModulesByName(moduleName));
    }

}