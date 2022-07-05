package com.example.parking_system.Repo;

import com.example.parking_system.model.Car;
import com.example.parking_system.model.Park;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepo extends JpaRepository<Car, Integer> {

//     List<Car> findAllByCustomer_Id(Integer id);
}
