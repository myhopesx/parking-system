package com.example.parking_system.Repo;

import com.example.parking_system.model.Car;
import com.example.parking_system.model.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepo extends JpaRepository<Car, Integer> {


    @Query(value = "SELECT * FROM parking_system.car where customer_id=?",nativeQuery = true)
     List<Car> findAllByCustomer_Id(Integer customer_id);
}
