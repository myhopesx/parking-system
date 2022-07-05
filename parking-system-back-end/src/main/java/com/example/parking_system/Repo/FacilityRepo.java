package com.example.parking_system.Repo;

import com.example.parking_system.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacilityRepo extends JpaRepository<Facility, Integer> {

     @Query(value = "SELECT * FROM parking_system.facility where facility_admin_id=?",nativeQuery = true)
     List<Facility> findAllByFacilityAdmin_Id(Integer id);

//     @Override
//     List<Facility> findAllBy(Integer integers);
}
