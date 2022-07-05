package com.example.parking_system.Controller;

import com.example.parking_system.Service.ParkService;
import com.example.parking_system.Service.ReservationService;
import com.example.parking_system.model.API;
import com.example.parking_system.model.Park;
import com.example.parking_system.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

     private final ReservationService reservationService;
     private
     Logger logger = LoggerFactory.getLogger(ReservationController.class);

     @GetMapping("/parks")
     public ResponseEntity<?> getAllParksByTime(@RequestParam("id") Integer facility_id,@RequestParam("startTime")String startTime,@RequestParam("endTime") String endTime) {
          logger.info("Get reservations");

          startTime = startTime.substring(0, 21);
          endTime = endTime.substring(0, 21);

          return ResponseEntity.status(200).body(reservationService.getAllReservedPark(facility_id,startTime,endTime));
     }

     @GetMapping("/{facility_id}")
     public ResponseEntity<List<Reservation>> getReservations(@PathVariable Integer facility_id) {
          logger.info("Get reservations");
          return ResponseEntity.status(200).body(reservationService.getReservations(facility_id));
     }

     @PostMapping("/{customer_id}/{facility_id}/{car_id}/{park_id}")
     public ResponseEntity<?> addReservation(@RequestBody Reservation reservation, @PathVariable Integer car_id, @PathVariable Integer customer_id, @PathVariable Integer facility_id, @PathVariable Integer park_id) throws ParseException {
          logger.info("Add reservation");

          return reservationService.addReservation(customer_id, car_id, facility_id, park_id, reservation);
     }


     @PutMapping("/{reservationid}")
     public ResponseEntity<API> update(@RequestBody @Valid Reservation reservation, @PathVariable Integer reservationid) {
          logger.info("Update reservations");
          reservationService.update(reservation, reservationid);
          return ResponseEntity.status(201).body(new API("Reservation updated", 201));
     }

     @DeleteMapping("/{reservationid}")
     public ResponseEntity<API> delete(@PathVariable Integer reservationid) {
          logger.info("Delete reservations");
          reservationService.delete(reservationid);
          return ResponseEntity.status(201).body(new API("Reservation deleted", 201));
     }

}
