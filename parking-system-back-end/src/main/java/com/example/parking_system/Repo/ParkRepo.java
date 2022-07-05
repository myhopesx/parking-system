package com.example.parking_system.Repo;

import com.example.parking_system.model.MyUser;
import com.example.parking_system.model.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkRepo extends JpaRepository<Park, Integer> {

     @Query(value = "SELECT COUNT(park_id) FROM parking_system.reservation where park_id=? AND start_time < now() AND now()< end_time", nativeQuery = true)
     Integer checkParkAvailability(Integer parkID);// checks if the park is available

     @Query(value = "SELECT * FROM parking_system.park where facility_id=?",nativeQuery = true)
     List<Park> findAllByFacilityId(Integer id);
}
