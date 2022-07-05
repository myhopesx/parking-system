package com.example.parking_system.Controller;

import com.example.parking_system.Service.CustomerServices;
import com.example.parking_system.model.Customer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

     private final CustomerServices customerServices;
     Logger logger = LoggerFactory.getLogger(MyUserController.class);

     @GetMapping("/all")
     public ResponseEntity<List<Customer>> getAllCustomers() {
          logger.info("Get customers");
          return ResponseEntity.status(200).body(customerServices.getAllCustomers());
     }

     @GetMapping("/{customer_id}")
     public ResponseEntity<?> getCustomerById(@PathVariable Integer customer_id) {
          logger.info("Get customers");

          ResponseEntity<String> Customer_Not_Found = checkCustomerAuthorized(customer_id);

          if (Customer_Not_Found != null) return Customer_Not_Found;


          return ResponseEntity.status(200).body(customerServices.getCustomerById(customer_id).get());
     }

     @PostMapping("/register")
     public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
          Customer c = customerServices.addCustomer(customer);
          if (c == null) {
               return ResponseEntity.status(404).body("Customer Already Registered");
          }

          logger.info("new customer added");
          return ResponseEntity.status(201).body(c);
     }

     @PutMapping("/{customer_id}")
     public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable Integer customer_id) {
          logger.info("Update customer");

          ResponseEntity<String> Customer_Not_Found = checkCustomerAuthorized(customer_id);

          if (Customer_Not_Found != null) return Customer_Not_Found;

          return customerServices.updateCustomer(customer_id, customer);
     }

     @DeleteMapping("/{customer_id}")
     public ResponseEntity<?> deleteCustomer(@PathVariable Integer customer_id) {
          ResponseEntity<String> Customer_Not_Found = checkCustomerAuthorized(customer_id);

          if (Customer_Not_Found != null) return Customer_Not_Found;

          return customerServices.deleteCustomer(customer_id);
     }

     private ResponseEntity<String> checkCustomerAuthorized(Integer customer_id) {
          logger.info("delete customer");
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
