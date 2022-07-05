package com.example.parking_system.Repo;

import com.example.parking_system.model.FacilityAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityAdminRepo extends JpaRepository<FacilityAdmin, Integer> {

     Optional<FacilityAdmin> findFacilityAdminByMyUser_Email(String email);

}
