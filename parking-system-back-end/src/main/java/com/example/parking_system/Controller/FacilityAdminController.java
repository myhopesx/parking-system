package com.example.parking_system.Controller;

import com.example.parking_system.Service.FacilityAdminService;
import com.example.parking_system.Service.FacilityService;
import com.example.parking_system.model.Customer;
import com.example.parking_system.model.Facility;
import com.example.parking_system.model.FacilityAdmin;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/facilities-admins")
@RequiredArgsConstructor
public class FacilityAdminController {

     private final FacilityAdminService facilityAdminService;
     private final FacilityService facilityService;

     Logger logger = LoggerFactory.getLogger(MyUserController.class);

     @GetMapping("/all")
     public ResponseEntity<List<FacilityAdmin>> getAllFacilityAdmins() {
          logger.info("Get facilityAdmin");
          return ResponseEntity.status(200).body(facilityAdminService.getAllFacilityAdmins());
     }

     @GetMapping("/{owner_id}")
     public ResponseEntity<?> getFacilityAdminById(@PathVariable Integer owner_id) {
          logger.info("Get facilityAdmin");

          ResponseEntity<String> FacilityAdmin_Not_Found = checkFacilityAdminAuthorized(owner_id);
          if (FacilityAdmin_Not_Found != null) return FacilityAdmin_Not_Found;

          return ResponseEntity.status(200).body(facilityAdminService.getFacilityAdminById(owner_id).get());
     }

     @PostMapping("/register")
     public ResponseEntity<?> addFacilityAdmin(@RequestBody FacilityAdmin facilityAdmin) {
          FacilityAdmin c = facilityAdminService.addFacilityAdmin(facilityAdmin);
          if (c == null) {
               return ResponseEntity.status(404).body("FacilityAdmin Already Registered");
          }

          logger.info("new facilityAdmin added");
          return ResponseEntity.status(201).body(c);
     }

     @PutMapping("/{owner_id}")
     public ResponseEntity<?> updateFacilityAdmin(@RequestBody FacilityAdmin facilityAdmin, @PathVariable Integer owner_id) {
          logger.info("Update facilityAdmin");

          ResponseEntity<String> FacilityAdmin_Not_Found = checkFacilityAdminAuthorized(owner_id);
          if (FacilityAdmin_Not_Found != null) return FacilityAdmin_Not_Found;

          return facilityAdminService.updateFacilityAdmin(owner_id, facilityAdmin);
     }

     @DeleteMapping("/{owner_id}")
     public ResponseEntity<?> deleteFacilityAdmin(@PathVariable Integer owner_id) {
          logger.info("Update facilityAdmin");

          ResponseEntity<String> FacilityAdmin_Not_Found = checkFacilityAdminAuthorized(owner_id);
          if (FacilityAdmin_Not_Found != null) return FacilityAdmin_Not_Found;

          return facilityAdminService.deleteFacilityAdmin(owner_id);
     }

     @GetMapping("/{owner_id}/facilities")
     public ResponseEntity<?> getAllFacilitiesForAdmin(@PathVariable Integer owner_id) {
          logger.info("Get facilities for facilityAdmin");

          ResponseEntity<String> FacilityAdmin_Not_Found = checkFacilityAdminAuthorized(owner_id);
          if (FacilityAdmin_Not_Found != null) return FacilityAdmin_Not_Found;

          return ResponseEntity.status(200).body(facilityService.getAllFacilityForAdmin(owner_id));
     }

     private ResponseEntity<String> checkFacilityAdminAuthorized(Integer owner_id) {
          Optional<FacilityAdmin> facilityAdmin = facilityAdminService.getFacilityAdminById(owner_id);
          if (!facilityAdmin.isPresent()) {
               return ResponseEntity.status(404).body("FacilityAdmin Not Found");
          }

          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          if (!facilityAdmin.get().getMyUser().getEmail().equalsIgnoreCase(auth.getName())) {
               return ResponseEntity.status(401).body("Unauthorized");
          }
          return null;
     }
}
