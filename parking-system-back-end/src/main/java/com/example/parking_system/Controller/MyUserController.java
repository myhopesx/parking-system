package com.example.parking_system.Controller;

import com.example.parking_system.Service.MyUserService;
import com.example.parking_system.model.API;
import com.example.parking_system.model.MyUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class MyUserController {

     private final MyUserService myUserService;
     Logger logger = LoggerFactory.getLogger(MyUserController.class);

     @GetMapping
     public ResponseEntity<List<MyUser>> getUsers() {
          logger.info("Get users");
          return ResponseEntity.status(200).body(myUserService.getUsers());
     }

     @PostMapping
     public ResponseEntity<API> addUser(@RequestBody @Valid MyUser myUser) {
          logger.info("Add user");
          myUserService.addUser(myUser);
          return ResponseEntity.status(201).body(new API("User Added", 201));
     }

     @PutMapping("/update/{userid}")
     public ResponseEntity<API> update(@RequestBody @Valid MyUser myUser, @PathVariable Integer userid) {
          logger.info("Update user");
          myUserService.update(myUser, userid);
          return ResponseEntity.status(201).body(new API("User updated", 201));
     }

     @DeleteMapping("/delete/{userid}")
     public ResponseEntity<API> delete(@PathVariable Integer userid) {
          logger.info("Delete user");
          myUserService.delete(userid);
          return ResponseEntity.status(200).body(new API("User deleted", 200));
     }


//    @PostMapping("/checkreservation")
//    public ResponseEntity<API> securCheck(@RequestBody String carPlate){
//        logger.info("Check if user is have reservation ");
////        myUserService.securityCheck(carPlate);
//        return ResponseEntity.status(201).body(new API("Guest car is register ",201));
//    }
}
