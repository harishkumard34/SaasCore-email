
package com.techpuram.saascore.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.PermissionsRepo;
import com.techpuram.saascore.db.ProfilePermissionsRepo;
import com.techpuram.saascore.db.ProfileRepo;
import com.techpuram.saascore.entity.Permissions;
import com.techpuram.saascore.entity.ProfilePermissions;
import com.techpuram.saascore.entity.Profiles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
@Service
public class ProfilePermissionsService {

    
    @Autowired
    private ProfilePermissionsRepo profilePermissionsRepo;

    @Autowired
    private  PermissionsRepo permissionsRepository;

    @Autowired
    private  ProfileRepo profileRepository;

    
    public List<ProfilePermissions> getAllProfilePermissions() {
        return profilePermissionsRepo.findAll();  
    }

    public Map<String, Object> getProfilePermissions(Long profileId) {
        List<ProfilePermissions> profilePermission = profilePermissionsRepo.findPermissions(profileId);

        if (profilePermission.isEmpty()) {
            throw new RuntimeException("No permissions found for the given section.");
        }

        // Extract the profile name from the first record
        String profileName = profilePermission.get(0).getProfile().getProfileName();

        // Prepare the fields list
        List<Map<String, Object>> permissionList = profilePermission.stream().map(pp -> {
            Map<String, Object> fieldMap = new HashMap<>();
            fieldMap.put("permission_id", pp.getPermissions().getPermissionsId());
            fieldMap.put("permission_name", pp.getPermissions().getPermissionName());
            fieldMap.put("permission_Type", pp.getPermissions().getPermissionType());
            fieldMap.put("status", pp.getPermissions().getStatus());
            fieldMap.put("description", pp.getPermissions().getDescription());
            fieldMap.put("resource", pp.getPermissions().getResource());
            return fieldMap;
        }).collect(Collectors.toList());

        // Construct the response
        Map<String, Object> response = new HashMap<>();
        response.put("Profile_name", profileName);
        response.put("Permissions", permissionList);

        return response;
    }


    
    public Optional<ProfilePermissions> getProfilePermissionsById(Long id) {
        return profilePermissionsRepo.findById(id); 
    }
    
    public ProfilePermissions createProfilePermissions(ProfilePermissions profilePermissions,Long profileId,Long permissionsId) {

        Profiles profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // Validate if the Permission exists
        Permissions permissions = permissionsRepository.findById(permissionsId)
                .orElseThrow(() -> new RuntimeException("Permissions not found"));

        // Set the values in the ProfilePermissions entity
        profilePermissions.setProfile(profile);
        profilePermissions.setPermissions(permissions);
        profilePermissions.setCreatedTime(System.currentTimeMillis());
        profilePermissions.setModifiedTime(System.currentTimeMillis());
        profilePermissions.setIsActive(true); 
        //profilePermissions.setModifiedBy(1L);
        // Save the ProfilePermissions entity
        return profilePermissionsRepo.save(profilePermissions);
    }
    
 
    
    public ProfilePermissions updateProfilePermissions(Long id, ProfilePermissions profilePermissions) {
        
        if (profilePermissionsRepo.existsById(id)) {
            profilePermissions.setProfilePermissionId(id);
            profilePermissions.setModifiedTime(System.currentTimeMillis()); 
            
            return profilePermissionsRepo.save(profilePermissions);
        } else {
            return null;
        }
    }

    public boolean deleteProfilePermissions(Long id) {
        if (profilePermissionsRepo.existsById(id)) {
            profilePermissionsRepo.deleteById(id);  
            return true;  
        } else {
            return false; 
        }
    }
}
