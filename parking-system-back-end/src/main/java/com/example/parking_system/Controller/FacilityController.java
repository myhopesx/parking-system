package com.example.parking_system.Controller;

import com.example.parking_system.Service.FacilityAdminService;
import com.example.parking_system.Service.FacilityService;
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
@RequestMapping("api/v1/facilities")
@RequiredArgsConstructor
public class FacilityController {

     private final FacilityService facilityService;
     private final FacilityAdminService facilityAdminService;

     Logger logger = LoggerFactory.getLogger(MyUserController.class);

     @GetMapping("/all")
     public ResponseEntity<List<Facility>> getAllFacility() {
          logger.info("Get facility");
          return ResponseEntity.status(200).body(facilityService.getAllFacility());
     }

     @GetMapping("/{facility_id}")
     public ResponseEntity<?> getFacilityById(@PathVariable Integer facility_id) {
          logger.info("Get facility");

          Optional<Facility> facility = facilityService.getFacilityById(facility_id);
          if(!facility.isPresent()){
               return ResponseEntity.status(404).body("Facility Not Found");
          }
          return ResponseEntity.status(200).body(facility.get());
     }

     @PostMapping("/{facility_admin_id}")
     public ResponseEntity<?> addFacility(@RequestBody Facility facility, @PathVariable Integer facility_admin_id) {

          ResponseEntity<String> FacilityAdmin_Not_Found = checkFacilityAdminAuthorized(facility_admin_id);
          if (FacilityAdmin_Not_Found != null) return FacilityAdmin_Not_Found;

          Facility f = facilityService.addFacility(facility, facility_admin_id);


          logger.info("new facility added");
          return ResponseEntity.status(201).body(f);
     }

     @PutMapping("/{facility_id}/{facility_admin_id}")
     public ResponseEntity<?> updateFacility(@RequestBody Facility facility, @PathVariable Integer facility_id, @PathVariable Integer facility_admin_id) {
          logger.info("Update facility");

          ResponseEntity<String> FacilityAdmin_Not_Found = checkFacilityAdminAuthorized(facility_admin_id);
          if (FacilityAdmin_Not_Found != null) return FacilityAdmin_Not_Found;

          return facilityService.updateFacility(facility_id, facility);
     }

     @DeleteMapping("/{facility_id}/{facility_admin_id}")
     public ResponseEntity<?> deleteFacility(@PathVariable Integer facility_id, @PathVariable Integer facility_admin_id) {
          logger.info("Update facility");

          ResponseEntity<String> FacilityAdmin_Not_Found = checkFacilityAdminAuthorized(facility_admin_id);
          if (FacilityAdmin_Not_Found != null) return FacilityAdmin_Not_Found;

          return facilityService.deleteFacility(facility_id);
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
