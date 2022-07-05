package com.example.parking_system.Service;

import com.example.parking_system.Repo.FacilityAdminRepo;
import com.example.parking_system.model.API;
import com.example.parking_system.model.FacilityAdmin;
import com.example.parking_system.model.MyUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FacilityAdminService {

     private final FacilityAdminRepo facilityAdminRepo;
     private final MyUserService myUserService;

     public List<FacilityAdmin> getAllFacilityAdmins() {
          return facilityAdminRepo.findAll();
     }

     public FacilityAdmin addFacilityAdmin(FacilityAdmin facilityAdmin) {
          MyUser user = myUserService.addUser(facilityAdmin.getMyUser());

          facilityAdmin.setMyUser(user);

          return facilityAdminRepo.save(facilityAdmin);
     }

     public Optional<FacilityAdmin> getFacilityAdminById(Integer facilityAdmin_id) {
          return facilityAdminRepo.findById(facilityAdmin_id);
     }

     public ResponseEntity<?> updateFacilityAdmin(Integer facilityAdmin_id, FacilityAdmin facilityAdmin) {
          Optional<FacilityAdmin> c = getFacilityAdminById(facilityAdmin_id);
          if(!c.isPresent()){
               return ResponseEntity.status(404).body(new API("FacilityAdmin Not Found", 404));
          }

          MyUser user = myUserService.update(facilityAdmin.getMyUser(), facilityAdmin.getMyUser().getId());

          c.get().setMyUser(user);
          facilityAdminRepo.save(c.get());

          return ResponseEntity.status(200).body(new API("FacilityAdmin Updated", 200));
     }


     public Optional<FacilityAdmin> getFacilityAdminByEmail(String email) {
          return facilityAdminRepo.findFacilityAdminByMyUser_Email(email);
     }

     public ResponseEntity<?> deleteFacilityAdmin(Integer facilityAdmin_id) {
          Optional<FacilityAdmin> fa = getFacilityAdminById(facilityAdmin_id);
          if(!fa.isPresent()){
               return ResponseEntity.status(404).body(new API("Customer Not Found", 404));
          }

          facilityAdminRepo.delete(fa.get());
          return ResponseEntity.status(200).body(new API("FacilityAdmin Deleted", 200));
     }
}
