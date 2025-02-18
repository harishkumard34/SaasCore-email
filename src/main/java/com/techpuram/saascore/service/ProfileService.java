package com.techpuram.saascore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.ProfileRepo;
import com.techpuram.saascore.entity.Profiles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final ProfileRepo profileRepo;

 
    public List<Profiles> getAllProfiles() {
        log.info("Fetching all profiles");
        return profileRepo.findAll();
    }


    public Optional<Profiles> getProfileById(Long id) {
        log.info("Fetching profile with ID: {}", id);
        return profileRepo.findById(id);
    }

    public Profiles createProfile(Profiles profile) {
        profile.setCreatedTime(System.currentTimeMillis());
        profile.setModifiedTime(System.currentTimeMillis());
        //log.info("Creating new profile: {}", profile.getProfileName());
        return profileRepo.save(profile);
    }

    // Update an existing profile
    public Profiles updateProfile(Long id, Profiles profile) {
        if (profileRepo.existsById(id)) {
            profile.setProfileId(id);
            profile.setModifiedTime(System.currentTimeMillis());
           // log.info("Updating profile with ID: {}", id);
            return profileRepo.save(profile);
        }
        log.warn("Profile with ID: {} not found for update", id);
        return null;
    }

    
    public boolean deleteProfile(Long id) {
        if (profileRepo.existsById(id)) {
            profileRepo.deleteById(id);
            //log.info("Profile with ID: {} has been deleted", id);
            return true;
        }
        //log.warn("Profile with ID: {} not found for deletion", id);
        return false;
    }

    public void deleteAllProfiles() {
        profileRepo.deleteAll();
        log.info("All profiles have been deleted");
    }

    public Profiles getProfileByName(String profileName) {
        return profileRepo.findByProfileName(profileName).orElse(null);
    }
}
