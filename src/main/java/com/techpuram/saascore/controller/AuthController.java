package com.techpuram.saascore.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpuram.saascore.entity.Auth;
import com.techpuram.saascore.service.AuthService;
import com.techpuram.saascore.service.AuthService.SessionGenerator;

import lombok.RequiredArgsConstructor;

/**
 * Controller for handling authentication-related requests.
 */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    /**
     * Handles user signup requests.
     * URL: http://localhost:8080/v1/auth/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<String> handleSignup(@RequestBody Auth auth) {
        auth.setCreatedTime(System.currentTimeMillis());
        String sessionId = SessionGenerator.generateSessionId();
        auth.setSessionId(sessionId);
        boolean isSaved = authService.saveAuth(auth);
        if (isSaved) {
            return ResponseEntity.ok("User successfully signed up.");
        } else {
            return ResponseEntity.badRequest().body("User with the same ID already exists or cannot be created.");
        }
    }

    /**
     * Handles user login requests.
     * URL: http://localhost:8080/v1/auth/login
     * 
     * @param loginRequest Request body containing login details (loginId, password).
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> handleLogin(@RequestBody Map<String, String> loginRequest) {
        String loginId = loginRequest.get("loginId");
        String password = loginRequest.get("password");

        String sessionId = authService.verifyLogin(loginId, password);
        if (sessionId != null) {
        Map<String, String> response = new HashMap<>();
        response.put("token", sessionId);
        response.put("message", "Login successful!");
        return ResponseEntity.ok(response);
        }   
        else {
        return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials."));
        }
    }

    /**
     * Retrieves session information.
     * URL: http://localhost:8080/v1/auth?action=sessioninfo
     */
    @GetMapping
    public ResponseEntity<String> getSessionInfo(@RequestParam(value = "action") String action,
                                                 @RequestHeader(value = "X-Session-ID") String sessionId) {
        if ("sessioninfo".equalsIgnoreCase(action)) {
            Auth auth = authService.getAuthBySessionId(sessionId);
            if (auth != null) {
                return ResponseEntity.ok(auth.toString());
            } else {
                return ResponseEntity.status(401).body("Invalid session ID.");
            }
        }
        return ResponseEntity.badRequest().body("Invalid action.");
    }

    /**
     * Handles password reset requests.
     * URL: http://localhost:8080/v1/auth/login?action=forgot
     */
    @PutMapping("/login")
    public ResponseEntity<String> handlePasswordReset(@RequestParam(value = "action") String action, @RequestBody Auth auth) {
        if ("forgot".equalsIgnoreCase(action)) {
            auth.setModifiedTime(System.currentTimeMillis());
            boolean isUpdated = authService.updateAuth(auth);
            if (isUpdated) {
                return ResponseEntity.ok("Password updated successfully.");
            } else {
                return ResponseEntity.badRequest().body("User not found.");
            }
        }
        return ResponseEntity.badRequest().body("Invalid action.");
    }
}
