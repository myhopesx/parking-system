package com.example.parking_system.Service;

import com.example.parking_system.Repo.CustomerRepo;
import com.example.parking_system.model.API;
import com.example.parking_system.model.Customer;
import com.example.parking_system.model.MyUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServices {

     private final CustomerRepo customerRepo;
     private final MyUserService myUserService;

     public List<Customer> getAllCustomers() {
          return customerRepo.findAll();
     }

     public Customer addCustomer(Customer customer) {
          MyUser user = myUserService.addUser(customer.getMyUser());

          customer.setMyUser(user);

          return customerRepo.save(customer);
     }

     public Optional<Customer> getCustomerById(Integer customer_id) {
          return customerRepo.findById(customer_id);
     }

     public ResponseEntity<?> updateCustomer(Integer customer_id, Customer customer) {
          Optional<Customer> c = getCustomerById(customer_id);
          if(!c.isPresent()){
               return ResponseEntity.status(404).body(new API("Customer Not Found", 404));
          }

          MyUser user = myUserService.update(customer.getMyUser(), customer.getMyUser().getId());

          c.get().setMyUser(user);
          customerRepo.save(c.get());

          return ResponseEntity.status(200).body(new API("Customer Updated", 200));
     }


     public Optional<Customer> getCustomerByEmail(String email) {
          return customerRepo.findCustomerByMyUser_Email(email);
     }

     public ResponseEntity<?> deleteCustomer(Integer customer_id) {
          Optional<Customer> c = getCustomerById(customer_id);
          if(!c.isPresent()){
               return ResponseEntity.status(404).body(new API("Customer Not Found", 404));
          }

          customerRepo.delete(c.get());
          return ResponseEntity.status(200).body(new API("Customer Deleted", 200));
     }

     public Optional<MyUser> getCustomerByEmailAndPassword(String email , String password) {
          return myUserService.getCustomerByEmailAndPassword(email,password);
     }

}
