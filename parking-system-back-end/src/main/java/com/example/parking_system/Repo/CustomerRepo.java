package com.example.parking_system.Repo;

import com.example.parking_system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

     Optional<Customer> findCustomerByMyUser_Email(String email);
}
