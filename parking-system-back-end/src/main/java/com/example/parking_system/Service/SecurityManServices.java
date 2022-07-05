package com.example.parking_system.Service;

import com.example.parking_system.Repo.SecurityManRepo;
import com.example.parking_system.model.API;
import com.example.parking_system.model.Facility;
import com.example.parking_system.model.SecurityMan;
import com.example.parking_system.model.MyUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityManServices {

     private final SecurityManRepo securityManRepo;
     private final MyUserService myUserService;
     private final FacilityService facilityService;

     public List<SecurityMan> getAllSecurityMan() {
          return securityManRepo.findAll();
     }

     public SecurityMan addSecurityMan(SecurityMan securityMan, Integer facility_id) {
          Optional<Facility> facility = facilityService.getFacilityById(facility_id);
          if(!facility.isPresent()){
               return null;
          }

          MyUser user = myUserService.addUser(securityMan.getMyUser());

          securityMan.setMyUser(user);
          securityMan.setFacility(facility.get());

          return securityManRepo.save(securityMan);
     }

     public Optional<SecurityMan> getSecurityManById(Integer customer_id) {
          return securityManRepo.findById(customer_id);
     }

     public ResponseEntity<?> updateSecurityMan(Integer securityMan_id, SecurityMan securityMan) {
          Optional<SecurityMan> c = getSecurityManById(securityMan_id);
          if(!c.isPresent()){
               return ResponseEntity.status(404).body(new API("SecurityMan Not Found", 404));
          }

          MyUser user = myUserService.update(securityMan.getMyUser(), securityMan.getMyUser().getId());

          c.get().setMyUser(user);
          securityManRepo.save(c.get());

          return ResponseEntity.status(200).body(new API("SecurityMan Updated", 200));
     }

     public Optional<SecurityMan> getSecurityManByEmail(String email) {
          return securityManRepo.findSecurityManByMyUser_Email(email);
     }

     public ResponseEntity<?> deleteSecurityMan(Integer securityMan_id) {
          Optional<SecurityMan> securityMan = getSecurityManById(securityMan_id);
          if(!securityMan.isPresent()){
               return ResponseEntity.status(404).body(new API("SecurityMan Not Found", 404));
          }

          securityManRepo.delete(securityMan.get());
          return ResponseEntity.status(200).body(new API("SecurityMan Deleted", 200));
     }

}
