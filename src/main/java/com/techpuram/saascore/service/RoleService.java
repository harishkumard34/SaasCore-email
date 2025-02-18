package com.techpuram.saascore.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.RoleRepository;
import com.techpuram.saascore.entity.Roles;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }

    public Roles getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Roles createRole(Roles role) {
        if (role.getReportingToId() != null) {
            Roles parentRole = roleRepository.findById(role.getReportingToId()).orElse(null);
            if (parentRole != null) {
                role.setLevel(parentRole.getLevel() + 1);
            } else {
                role.setLevel(0); // Default to 0 if no parent found
            }
        } else {
            role.setLevel(0); // Root level
        }
        return roleRepository.save(role);
    }

    public Roles updateRole(Long id, Roles role) {
        Roles existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole != null) {
            existingRole.setRoleName(role.getRoleName());
            existingRole.setReportingToId(role.getReportingToId());
            existingRole.setShareWithPeers(role.isShareWithPeers());
            existingRole.setDescription(role.getDescription());
            existingRole.setCreatedBy(role.getCreatedBy());
            existingRole.setModifiedBy(role.getModifiedBy());
            existingRole.setCreatedTime(role.getCreatedTime());
            existingRole.setModifiedTime(role.getModifiedTime());

            if (role.getReportingToId() != null) {
                Roles parentRole = roleRepository.findById(role.getReportingToId()).orElse(null);
                if (parentRole != null) {
                    existingRole.setLevel(parentRole.getLevel() + 1);
                } else {
                    existingRole.setLevel(0); // Default to 0 if no parent found
                }
            } else {
                existingRole.setLevel(0); // Root level
            }
            return roleRepository.save(existingRole);
        }
        return null;
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
        log.info("All roles have been deleted");
}

    public Roles getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElse(null);
    }
}
