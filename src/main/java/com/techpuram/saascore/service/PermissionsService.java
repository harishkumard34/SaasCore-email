package com.techpuram.saascore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.PermissionsRepo;
import com.techpuram.saascore.entity.Permissions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionsService {

    private final PermissionsRepo permissionRepository;

    public List<Permissions> getAllPermissions() {
        //log.info("Fetching all permissions");
        return permissionRepository.findAll();
    }

    
    public Permissions getPermissionById(Long id) {
        //log.info("Fetching permission with ID: {}", id);
        return permissionRepository.findById(id).orElse(null);
    }

   
    public Permissions createPermission(Permissions permission) {
        permission.setCreatedTime(System.currentTimeMillis());
        permission.setModifiedTime(System.currentTimeMillis());
       

        log.info("Creating new permission with ID: {}", permission.getPermissionsId());
        return permissionRepository.save(permission);
    }

   
    public Permissions updatePermission(Long id, Permissions permission) {
        if (permissionRepository.existsById(id)) {
            permission.setPermissionsId(id);
            permission.setModifiedTime(System.currentTimeMillis());
          

            log.info("Updating permission with ID: {}", permission.getPermissionsId());
            return permissionRepository.save(permission);
        }
        log.warn("Permission with ID: {} not found for update", id);
        return null;
    }

    
    public boolean deletePermission(Long id) {
        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
            log.info("Permission with ID: {} has been deleted", id);
            return true;
        }
        log.warn("Permission with ID: {} not found for deletion", id);
        return false;
    }

    public void deleteAllPermissions() {
        permissionRepository.deleteAll();
        log.info("All permissions have been deleted");
    }
}