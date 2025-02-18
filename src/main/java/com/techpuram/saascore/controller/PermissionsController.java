package com.techpuram.saascore.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techpuram.saascore.entity.Permissions;
import com.techpuram.saascore.service.PermissionsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/permissions")


public class PermissionsController {
    @Autowired
    private final PermissionsService permissionService;

    
    @GetMapping
    public ResponseEntity<List<Permissions>> getPermissions() {
        List<Permissions> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok().body(permissions);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Permissions> getPermissionById(@PathVariable Long id) {
        Permissions permission = permissionService.getPermissionById(id);
        return permission != null ? ResponseEntity.ok().body(permission) : ResponseEntity.notFound().build();
    }

    
    @PostMapping
    public ResponseEntity<Permissions> createPermission(@RequestBody Permissions permission) {
        Permissions createdPermission = permissionService.createPermission(permission);
        return ResponseEntity.ok().body(createdPermission);
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<Permissions> updatePermission(@PathVariable Long id, @RequestBody Permissions permission) {
        Permissions updatedPermission = permissionService.updatePermission(id, permission);
        return updatedPermission != null ? ResponseEntity.ok().body(updatedPermission) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        boolean isDeleted = permissionService.deletePermission(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllPermissions() {
        permissionService.deleteAllPermissions();
        return ResponseEntity.ok().build();
    }
}


