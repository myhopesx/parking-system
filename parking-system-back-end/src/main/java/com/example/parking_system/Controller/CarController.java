package com.example.parking_system.Controller;

import com.example.parking_system.Service.CarService;
import com.example.parking_system.Service.CustomerServices;
import com.example.parking_system.Service.ParkService;
import com.example.parking_system.model.API;
import com.example.parking_system.model.Car;
import com.example.parking_system.model.Customer;
import com.example.parking_system.model.Park;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/cars")
@RequiredArgsConstructor
public class CarController {

     private final CarService carService;
     private final CustomerServices customerServices;
     Logger logger = LoggerFactory.getLogger(CarController.class);

     @GetMapping("/{customer_id}")
   public ResponseEntity<?> getCarsByCustomerId(@AuthenticationPrincipal Customer customer  , @PathVariable Integer customer_id) {
//         ResponseEntity<String> Customer_Not_Found = checkCustomerAuthorized(customer_id);
//
//         if (Customer_Not_Found != null) return Customer_Not_Found;

          return ResponseEntity.status(200).body(carService.getCarsByCustomerId(customer_id));
  }

     @PostMapping("/{customer_id}")
     public ResponseEntity<?> addCar(@RequestBody @Valid Car car, @PathVariable Integer customer_id) {
          logger.info("Add park");

//          ResponseEntity<String> Customer_Not_Found = checkCustomerAuthorized(customer_id);
//
//          if (Customer_Not_Found != null) return Customer_Not_Found;

          carService.addCar(car, customer_id);
          return ResponseEntity.status(201).body(new API("Car Added", 201));
     }

     @PutMapping("/{customer_id}")
     public ResponseEntity<?> update(@RequestBody @Valid Car car, @PathVariable Integer customer_id) {
          logger.info("Update park");

          ResponseEntity<String> Customer_Not_Found = checkCustomerAuthorized(customer_id);

          if (Customer_Not_Found != null) return Customer_Not_Found;


          carService.update(car, customer_id);
          return ResponseEntity.status(201).body(new API("Car updated", 201));
     }

     @DeleteMapping("/{customer_id}/{car_id}")
     public ResponseEntity<?> delete(@PathVariable Integer customer_id, @PathVariable Integer car_id) {
          logger.info("Delete park");

          ResponseEntity<String> Customer_Not_Found = checkCustomerAuthorized(customer_id);

          if (Customer_Not_Found != null) return Customer_Not_Found;

          carService.delete(car_id);
          return ResponseEntity.status(201).body(new API("Car deleted", 201));
     }

     private ResponseEntity<String> checkCustomerAuthorized(Integer customer_id) {
          Optional<Customer> c = customerServices.getCustomerById(customer_id);
          if (!c.isPresent()) {
               return ResponseEntity.status(404).body("Customer Not Found");
          }

          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          if (!c.get().getMyUser().getEmail().equalsIgnoreCase(auth.getName())) {
               return ResponseEntity.status(401).body("Unauthorized");
          }

          return null;
     }
}
