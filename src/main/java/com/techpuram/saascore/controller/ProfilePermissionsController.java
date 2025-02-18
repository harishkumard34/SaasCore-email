package com.techpuram.saascore.controller;


import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.techpuram.saascore.entity.ProfilePermissions;
import com.techpuram.saascore.service.ProfilePermissionsService;

@RestController
@RequestMapping("/v1/profilePermissions")
public class ProfilePermissionsController {

    @Autowired
    private ProfilePermissionsService profilePermissionsService;
    

    
    @GetMapping
    public List<ProfilePermissions> getAllProfilePermissions() {
        return profilePermissionsService.getAllProfilePermissions();
    }

    // Get ProfilePermissions by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfilePermissions> getProfilePermissionsById(@PathVariable Long id) {
        Optional<ProfilePermissions> profilePermissions = profilePermissionsService.getProfilePermissionsById(id);
        if (profilePermissions.isPresent()) {
            return ResponseEntity.ok(profilePermissions.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{profileId}/permissions")
    public ResponseEntity<Map<String, Object>> getSectionWithFields(@PathVariable Long profileId) {
        Map<String, Object> response = profilePermissionsService.getProfilePermissions(profileId);
        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/create/{profileId}/{permissionsId}")
    public ResponseEntity<ProfilePermissions> createProfilePermissions( @Validated @RequestBody ProfilePermissions profilePermissions,@PathVariable Long profileId ,@PathVariable Long permissionsId) {
        ProfilePermissions createdProfilePermission= profilePermissionsService.createProfilePermissions(profilePermissions,profileId,permissionsId);
        return ResponseEntity.ok().body(createdProfilePermission);
    }

    // Update an existing ProfilePermissions
    @PutMapping("/{id}")
    public ResponseEntity<ProfilePermissions> updateProfilePermissions(@PathVariable Long id, 
                                                                        @RequestBody ProfilePermissions profilePermissions) {
        ProfilePermissions updatedProfilePermissions = profilePermissionsService.updateProfilePermissions(id, profilePermissions);
        if (updatedProfilePermissions != null) {
            return ResponseEntity.ok(updatedProfilePermissions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete ProfilePermissions by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfilePermissions(@PathVariable Long id) {
        boolean deleted = profilePermissionsService.deleteProfilePermissions(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

