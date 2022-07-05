package com.example.parking_system.Service;

import com.example.parking_system.Repo.FacilityRepo;
import com.example.parking_system.model.*;
import com.example.parking_system.model.Facility;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FacilityService {

     private final FacilityRepo facilityRepo;
     private final FacilityAdminService facilityAdminService;

     public List<Facility> getAllFacility() {
          return facilityRepo.findAll();
     }

     public Facility addFacility(Facility facility, Integer facility_admin_id) {
          Optional<FacilityAdmin> fa = facilityAdminService.getFacilityAdminById(facility_admin_id);
          if(!fa.isPresent()){
               return null;
          }

          facility.setFacilityAdmin(fa.get());
          return facilityRepo.save(facility);
     }

     public Optional<Facility> getFacilityById(Integer facilityAdmin_id) {
          return facilityRepo.findById(facilityAdmin_id);
     }

     public ResponseEntity<?> updateFacility(Integer facility_id, Facility facility) {
          Optional<Facility> currentFacility = getFacilityById(facility_id);
          if (!currentFacility.isPresent()) {
               return ResponseEntity.status(404).body(new API("Facility Not Found", 404));
          }

          currentFacility.get().setIsActive(facility.getIsActive());
          currentFacility.get().setAddress(facility.getAddress());
          currentFacility.get().setStartTime(facility.getStartTime());
          currentFacility.get().setEndTime(facility.getEndTime());
          currentFacility.get().setName(facility.getName());
          currentFacility.get().setDescription(facility.getDescription());

          facilityRepo.save(currentFacility.get());

          return ResponseEntity.status(200).body(new API("Facility Updated", 200));
     }

     public ResponseEntity<?> deleteFacility(Integer facilityAdmin_id) {
          Optional<Facility> fa = getFacilityById(facilityAdmin_id);
          if (!fa.isPresent()) {
               return ResponseEntity.status(404).body(new API("Facility Not Found", 404));
          }

          facilityRepo.delete(fa.get());
          return ResponseEntity.status(200).body(new API("Facility Deleted", 200));
     }

     public List<Facility> getAllFacilityForAdmin(Integer owner_id) {
          return facilityRepo.findAllByFacilityAdmin_Id(owner_id);
     }

}
