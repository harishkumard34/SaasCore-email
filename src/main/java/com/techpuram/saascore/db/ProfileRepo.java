package com.techpuram.saascore.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techpuram.saascore.entity.Profiles;

@Repository
public interface ProfileRepo extends JpaRepository<Profiles, Long> {
    Optional<Profiles> findByProfileName(String profileName);
}