package com.example.parking_system.Security;

import com.example.parking_system.Service.CustomerServices;
import com.example.parking_system.Service.SecurityManServices;
import com.example.parking_system.model.Customer;
import com.example.parking_system.model.SecurityMan;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityManDetailsService implements UserDetailsService {
     private final SecurityManServices securityManServices;

     @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
          Optional<SecurityMan> securityMan= securityManServices.getSecurityManByEmail(email);

          if(!securityMan.isPresent()){
               throw new UsernameNotFoundException("securityMan not found");
          }

          return securityMan.get();
     }
}
