package com.example.parking_system.Controller;

import com.example.parking_system.Service.MyUserService;
import com.example.parking_system.Service.ParkService;
import com.example.parking_system.model.API;
import com.example.parking_system.model.MyUser;
import com.example.parking_system.model.Park;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/parks")
@RequiredArgsConstructor
public class ParkController {

     private final ParkService parkService;
     Logger logger = LoggerFactory.getLogger(ParkController.class);

     @GetMapping("/{facility_id}")
     public ResponseEntity<List<Park>> getParksByFacilityId(@PathVariable Integer facility_id) {
          logger.info("Get parks by facility id");
          return ResponseEntity.status(200).body(parkService.getParksByFacilityId(facility_id));
     }

     @PostMapping("/{facility_id}")
     public ResponseEntity<API> ownerAddPark(@RequestBody @Valid Park park, @PathVariable Integer facility_id) {
          logger.info("Add park");
          Park p = parkService.addPark(park, facility_id);
          if (p == null) {
               return ResponseEntity.status(404).body(new API("Park Not Found", 404));
          }

          return ResponseEntity.status(201).body(new API("Park ready to use", 201));
     }

     @PutMapping("/{parkid}")
     public ResponseEntity<API> update(@RequestBody @Valid Park park, @PathVariable Integer parkid) {
          logger.info("Update park");
          parkService.update(park, parkid);
          return ResponseEntity.status(201).body(new API("Park updated", 201));
     }

     @DeleteMapping("/{parkid}")
     public ResponseEntity<API> delete(@PathVariable Integer parkid) {
          logger.info("Delete park");
          parkService.delete(parkid);
          return ResponseEntity.status(201).body(new API("Park deleted", 201));
     }

}
