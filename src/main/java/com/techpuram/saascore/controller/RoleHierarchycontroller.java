package com.techpuram.saascore.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techpuram.saascore.entity.RoleHierarchy;
import com.techpuram.saascore.service.RoleHierarchyService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/roleHierarchy")
@AllArgsConstructor
public class RoleHierarchycontroller {
    private final RoleHierarchyService roleHierarchyService;

    @GetMapping
    public List<RoleHierarchy> getAllRoleHierarchies() {
        return roleHierarchyService.getAllRoleHierarchies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleHierarchy> getRoleHierarchyById(@PathVariable Long id) {
        RoleHierarchy roleHierarchy = roleHierarchyService.getRoleHierarchyById(id);
        if (roleHierarchy != null) {
            return ResponseEntity.ok(roleHierarchy);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public RoleHierarchy createRoleHierarchy(@RequestBody RoleHierarchy roleHierarchy) {
        return roleHierarchyService.createRoleHierarchy(roleHierarchy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleHierarchy> updateRoleHierarchy(@PathVariable Long id, @RequestBody RoleHierarchy roleHierarchy) {
        RoleHierarchy updatedRoleHierarchy = roleHierarchyService.updateRoleHierarchy(id, roleHierarchy);
        if (updatedRoleHierarchy != null) {
            return ResponseEntity.ok(updatedRoleHierarchy);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleHierarchy(@PathVariable Long id) {
        roleHierarchyService.deleteRoleHierarchy(id);
        return ResponseEntity.noContent().build();
    }
}