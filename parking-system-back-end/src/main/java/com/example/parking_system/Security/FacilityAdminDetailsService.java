package com.example.parking_system.Security;

import com.example.parking_system.Service.CustomerServices;
import com.example.parking_system.Service.FacilityAdminService;
import com.example.parking_system.model.Customer;
import com.example.parking_system.model.FacilityAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacilityAdminDetailsService implements UserDetailsService {

     private final FacilityAdminService facilityAdminService;

     @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
          Optional<FacilityAdmin> facilityAdmin= facilityAdminService.getFacilityAdminByEmail(email);

          if(!facilityAdmin.isPresent()){
               throw new UsernameNotFoundException("customer not found");
          }

          return facilityAdmin.get();
     }


}
