package com.example.parking_system.Repo;

import com.example.parking_system.model.SecurityMan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityManRepo extends JpaRepository<SecurityMan, Integer> {

     Optional<SecurityMan> findSecurityManByMyUser_Email(String email);
}
