package com.example.parking_system.Service;

import com.example.parking_system.Exception.InvalidIdException;
import com.example.parking_system.Repo.ParkRepo;
import com.example.parking_system.model.Facility;
import com.example.parking_system.model.Park;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkService {

     private final ParkRepo parkRepo;
     private final FacilityService facilityService;

     public List<Park> getParks() {
          return parkRepo.findAll();
     }

     public Park addPark(Park park, Integer facility_id) {
          Optional<Facility> facility = facilityService.getFacilityById(facility_id);
          if (!facility.isPresent()) {
               return null;
          }

          park.setFacility(facility.get());
          return parkRepo.save(park);
     }

     public Optional<Park> getParkById(Integer parkid) {
          return parkRepo.findById(parkid);
     }

     public void update(Park park, Integer parkid) {
          Optional<Park> currentPark = getParkById(parkid);
          if (!currentPark.isPresent()) {
               throw new InvalidIdException("Invalid id");
          }

          currentPark.get().setParkNumber(park.getParkNumber());
          currentPark.get().setSection(park.getSection());
          currentPark.get().setPricePerHour(park.getPricePerHour());

          parkRepo.save(currentPark.get());
     }

     public void delete(Integer parkid) {
          Optional<Park> currentPark = getParkById(parkid);
          if (!currentPark.isPresent()) {
               throw new InvalidIdException("Invalid id");
          }

          parkRepo.delete(currentPark.get());
     }

     public List<Park> getParksByFacilityId(Integer facility_id) {
          return parkRepo.findAllByFacilityId(facility_id);
     }
}
