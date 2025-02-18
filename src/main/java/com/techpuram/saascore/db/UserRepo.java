package com.techpuram.saascore.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpuram.saascore.entity.Users;

public interface UserRepo extends JpaRepository<Users, Long> {

}
