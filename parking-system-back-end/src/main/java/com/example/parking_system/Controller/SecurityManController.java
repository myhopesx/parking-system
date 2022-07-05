package com.example.parking_system.Controller;

import com.example.parking_system.Service.SecurityManServices;
import com.example.parking_system.model.SecurityMan;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/securities")
@RequiredArgsConstructor
public class SecurityManController {

     private final SecurityManServices securityManServices;
     Logger logger = LoggerFactory.getLogger(MyUserController.class);

     @GetMapping("/all")
     public ResponseEntity<List<SecurityMan>> getAllSecurityMans() {
          logger.info("Get SecurityMan");
          return ResponseEntity.status(200).body(securityManServices.getAllSecurityMan());
     }

     @GetMapping("/{securityMan_id}")
     public ResponseEntity<?> getSecurityManById(@PathVariable Integer securityMan_id) {
          logger.info("Get SecurityMan");

          Optional<SecurityMan> securityMan = securityManServices.getSecurityManById(securityMan_id);
          if(!securityMan.isPresent()){
               return ResponseEntity.status(404).body("SecurityMan Not Found");
          }
          return ResponseEntity.status(200).body(securityMan.get());
     }

     @PostMapping("/register/{facility_id}")
     public ResponseEntity<?> addSecurityMan(@RequestBody SecurityMan securityMan, @PathVariable Integer facility_id) {
          SecurityMan s = securityManServices.addSecurityMan(securityMan, facility_id);
          if (s == null) {
               return ResponseEntity.status(404).body("Facility Not Found");
          }

          logger.info("new securityMan added");
          return ResponseEntity.status(201).body(s);
     }

     @PutMapping("/{securityMan_id}")
     public ResponseEntity<?> updateSecurityMan(@RequestBody SecurityMan securityMan, @PathVariable Integer securityMan_id) {
          logger.info("Update securityMan");
          return securityManServices.updateSecurityMan(securityMan_id, securityMan);
     }

     @DeleteMapping("/{securityMan_id}")
     public ResponseEntity<?> deleteSecurityMan(@PathVariable Integer securityMan_id) {
          logger.info("Update securityMan");
          return securityManServices.deleteSecurityMan(securityMan_id);
     }
}
