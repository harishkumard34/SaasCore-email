package com.techpuram.saascore.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techpuram.saascore.db.ProfileRepo;
import com.techpuram.saascore.db.UserRepo;
import com.techpuram.saascore.entity.Profiles;
import com.techpuram.saascore.entity.Users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final ProfileRepo profileRepo;

    public List<Users> getAllUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    public Users getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        return userRepo.findById(id).orElse(null);
    }
    @Transactional
public Users createUser(Users user) {
    user.setCreatedTime(System.currentTimeMillis());
    user.setModifiedTime(System.currentTimeMillis());

    // Ensure profile is saved first
    if (user.getProfile() != null && user.getProfile().getProfileId() == null) {
        Profiles profile = user.getProfile();

        // Ensure profileName is not null
        if (profile.getProfileName() == null || profile.getProfileName().isEmpty()) {
            throw new IllegalArgumentException("Profile name cannot be null or empty");
        }

        profile.setCreatedTime(System.currentTimeMillis());
        profile.setModifiedTime(System.currentTimeMillis());
        profile = profileRepo.save(profile);
        user.setProfile(profile);
    }

    log.info("Creating new user with ID: {}", user.getUserId());
    return userRepo.save(user);
}
    public Users updateUser(Long id, Users user) {
        if (userRepo.existsById(id)) {
            user.setUserId(id);
            user.setModifiedTime(System.currentTimeMillis());

            log.info("Updating user with ID: {}", user.getUserId());
            return userRepo.save(user);
        }
        log.warn("User with ID: {} not found for update", id);
        return null;
    }

    public boolean deleteUser(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            log.info("User with ID: {} has been deleted", id);
            return true;
        }
        log.warn("User with ID: {} not found for deletion", id);
        return false;
    }

    public void deleteAllUsers() {
        userRepo.deleteAll();
        log.info("All users have been deleted");
    }
}
