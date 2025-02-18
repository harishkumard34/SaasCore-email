package com.techpuram.saascore.config;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    // Use a secure 256-bit secret key (Base64 encoded)
	@Value("${JWT_SECRET}")
    private String jwtSecret; 
    private final long jwtExpirationMs = 86400000; // 1 day

    // Convert the Base64-encoded secret key to a Key object
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateJwt(String username, String schema) {
        return Jwts.builder()
                .setSubject(username)
                .claim("schema", schema) // Schema for multi-tenancy
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims parseJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
