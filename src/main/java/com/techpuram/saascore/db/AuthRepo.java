package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import com.techpuram.saascore.entity.Auth;

import java.util.Optional;

/**
 * Repository is an interface that provides access to data in a database
 */
public interface AuthRepo extends JpaRepository<Auth, Integer> {
    
    Optional<Auth> findBySessionId(String sessionId);

    // Custom query to find an Auth object by loginId (authName, email, or phone)
    @Query("SELECT a FROM Auth a WHERE a.authName = :loginId OR a.email = :loginId OR a.phone = :loginId")
    Optional<Auth> findByLoginId(@Param("loginId") String loginId);
}
