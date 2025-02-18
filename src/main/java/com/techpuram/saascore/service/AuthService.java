package com.techpuram.saascore.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techpuram.saascore.db.AuthRepo;
import com.techpuram.saascore.entity.Auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service layer is where all the business logic lies
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthRepo authRepo;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TenantService tenantService;
    private final SignupAsynService signupAsynService;


    /**
     * Save a new auth entity during signup.
     * Automatically hashes the password and assigns a session ID.
     *
     * @param auth - Auth entity to save
     * @return true if saved successfully, false otherwise
     */
    public boolean saveAuth(Auth auth) {
       

        // Create tenant schema and run Flyway migrations
        Long tenantId = tenantService.registerTenant();

        String tenantName = tenantService.getTenantName();

        String hashedPassword = hashPassword(auth.getPasswordPhrase());
        auth.setPasswordPhrase(hashedPassword);
        auth.setCreatedTime(System.currentTimeMillis());
        auth.setTenantId(tenantId);
        String sessionId = SessionGenerator.generateSessionId();
        auth.setSessionId(sessionId);

        saveAuthInfo(auth);

        signupAsynService.createUserAsync(auth, tenantName, userService, this);

        log.info("Auth with ID {} created successfully.", auth.getAuthId());
        return true;
    }

    @Transactional
    private void saveAuthInfo(Auth auth) {
        authRepo.save(auth);
    }

    public String verifyLogin(String loginId, String password) {
        Optional<Auth> optionalAuth = authRepo.findByLoginId(loginId);
        if (optionalAuth.isPresent()) {
            Auth auth = optionalAuth.get();
            if (passwordEncoder.matches(password, auth.getPasswordPhrase())) { // Use matches instead of encode
                String newSessionId = SessionGenerator.generateSessionId();
                auth.setSessionId(newSessionId);
                authRepo.save(auth);
                log.info("Login successful for user {}.", loginId);
                return newSessionId;
            }
        }
        log.warn("Invalid login credentials for user {}.", loginId);
        return null;
    }

    public boolean updateAuth(Auth auth) {
        Optional<Auth> existingAuth = authRepo.findById(auth.getAuthId());
        if (existingAuth.isPresent()) {
            auth.setModifiedTime(System.currentTimeMillis());
            authRepo.save(auth);
            log.info("Auth with id: {} updated successfully", auth.getAuthId());
            return true;
        }
        log.info("Auth with id: {} not found. Update aborted.", auth.getAuthId());
        return false;
    }

    public Auth getAuthBySessionId(String sessionId) {
        return authRepo.findBySessionId(sessionId).orElse(null);
    }

    public String getTenant(Auth auth) {
        if (auth.getTenantId() != null) {
            return tenantService.getTenantName(auth.getTenantId());
        }
        return null;
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public static class SessionGenerator {
        public static String generateSessionId() {
            return UUID.randomUUID().toString();
        }
    }
}
