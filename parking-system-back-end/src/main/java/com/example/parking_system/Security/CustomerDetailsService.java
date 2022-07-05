package com.example.parking_system.Security;

import com.example.parking_system.Service.CustomerServices;
import com.example.parking_system.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService  implements UserDetailsService {
     private final CustomerServices customerService;

     @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
          Optional<Customer> customer= customerService.getCustomerByEmail(email);

          if(!customer.isPresent()){
               throw new UsernameNotFoundException("customer not found");
          }

          return customer.get();
     }
}
