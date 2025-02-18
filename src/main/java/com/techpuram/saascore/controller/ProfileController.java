package com.techpuram.saascore.controller;

import java.util.List;
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

import com.techpuram.saascore.entity.Profiles;
import com.techpuram.saascore.service.ProfileService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/profiles")
public class ProfileController {
    
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<Profiles>> getProfiles() {
        List<Profiles> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok().body(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profiles> getProfileById(@PathVariable("id") Long id) {
        Optional<Profiles> profile = profileService.getProfileById(id);
        return profile.isPresent() ? ResponseEntity.ok().body(profile.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Profiles> createProfile(@RequestBody Profiles profile) {
        Profiles createdProfile = profileService.createProfile(profile);
        return ResponseEntity.ok().body(createdProfile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profiles> updateProfile(@PathVariable("id") Long id, @RequestBody Profiles profile) {
        Profiles updatedProfile = profileService.updateProfile(id, profile);
        return updatedProfile != null ? ResponseEntity.ok().body(updatedProfile) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable("id") Long profileId) {
        try {
            log.info("Attempting to delete profile with ID: {}", profileId);
            boolean isDeleted = profileService.deleteProfile(profileId);
            return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting profile with ID: {}", profileId, e);
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAllProfiles() {
        profileService.deleteAllProfiles();
        return ResponseEntity.ok().build();
    }
}