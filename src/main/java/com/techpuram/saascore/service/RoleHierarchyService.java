package com.techpuram.saascore.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.RoleHierarchyRepo;
import com.techpuram.saascore.entity.RoleHierarchy;
import com.techpuram.saascore.entity.Roles;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class RoleHierarchyService {
    private final RoleHierarchyRepo roleHierarchyRepository;
    public List<RoleHierarchy> getAllRoleHierarchies() {
        return roleHierarchyRepository.findAll();
    }

    public RoleHierarchy getRoleHierarchyById(Long id) {
        return roleHierarchyRepository.findById(id).orElse(null);
    }

    public RoleHierarchy createRoleHierarchy(RoleHierarchy roleHierarchy) {
        // Calculate the distance dynamically before saving
        int distance = calculateDistance(roleHierarchy.getRole(), roleHierarchy.getParentRole());
        roleHierarchy.setDistance(distance);
        return roleHierarchyRepository.save(roleHierarchy);
    }

    public RoleHierarchy updateRoleHierarchy(Long id, RoleHierarchy roleHierarchy) {
        RoleHierarchy existingRoleHierarchy = roleHierarchyRepository.findById(id).orElse(null);
        if (existingRoleHierarchy != null) {
            existingRoleHierarchy.setRole(roleHierarchy.getRole());
            existingRoleHierarchy.setParentRole(roleHierarchy.getParentRole());
            existingRoleHierarchy.setDistance(calculateDistance(roleHierarchy.getRole(), roleHierarchy.getParentRole()));
            return roleHierarchyRepository.save(existingRoleHierarchy);
        }
        return null;
    }

    public void deleteRoleHierarchy(Long id) {
        roleHierarchyRepository.deleteById(id);
    }

    // Method to calculate the distance dynamically
    private int calculateDistance(Roles role, Roles parentRole) {
            // Roles role = roleRepository.findById(roleId).orElse(null);
            // Roles parentRole = roleRepository.findById(parentRoleId).orElse(null);

        if (role != null && parentRole != null) {
            return role.getLevel() - parentRole.getLevel();
        }
        return 0; // Default distance if roles are not found
    }
}

