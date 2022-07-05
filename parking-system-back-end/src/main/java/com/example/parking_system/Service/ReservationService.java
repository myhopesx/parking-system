package com.example.parking_system.Service;

import com.example.parking_system.Exception.InvalidIdException;
import com.example.parking_system.Exception.ParkNotAvailableException;
import com.example.parking_system.Repo.ParkRepo;
import com.example.parking_system.Repo.ReservationRepo;
import com.example.parking_system.model.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {

     private final ReservationRepo reservationRepo;
     private final ParkRepo parkRepo;
     private final ParkService parkService;
     private final CustomerServices customerServices;
     private final FacilityService facilityService;


     public List<Reservation> getReservations(Integer facility_id) {
          return reservationRepo.findAllByFacilityId(facility_id);
     }

     public ResponseEntity<?> addReservation(Integer customer_id, Integer car_id, Integer facility_id, Integer park_id, Reservation reservation) throws ParseException {
          Optional<Customer> c = customerServices.getCustomerById(customer_id);
          if (!c.isPresent()) {
               return ResponseEntity.status(404).body(new API("Customer Not Found", 404));
          }

          Optional<Facility> f = facilityService.getFacilityById(facility_id);
          if (!f.isPresent()) {
               return ResponseEntity.status(404).body(new API("Facility Not Found", 404));
          }

          Optional<Park> p = parkService.getParkById(park_id);
          if (!p.isPresent()) {
               return ResponseEntity.status(404).body(new API("Park Not Found", 404));
          }


          if (parkRepo.checkParkAvailability(reservation.getPark().getId()) > 0) {
               throw new ParkNotAvailableException(" Park is already reserved , please choose another parking. ");
          }

//          String dateStr = "Mon Jun 18 00:00:00 IST 2012"; yes

          DateFormat formatter = new SimpleDateFormat("E MMM dd yyyy HH:mm");
          Date startDate = formatter.parse(reservation.getStartTime().substring(0, 21));
          Date endDate = formatter.parse(reservation.getEndTime().substring(0, 21));

          Integer hours = endDate.getHours() - startDate.getHours();
          Double amount = hours * p.get().getPricePerHour();

          reservation.setAmount(amount);
          reservation.setCustomer(c.get());
          reservation.setFacility(f.get());
          reservation.setPark(p.get());
          reservation.setStartTime(reservation.getStartTime().substring(0, 21));
          reservation.setEndTime(reservation.getEndTime().substring(0, 21));

          reservationRepo.save(reservation);
          return ResponseEntity.status(201).body(new API("Reservation Added", 201));
     }

     public Optional<Reservation> getUReservationById(Integer reservationid) {
          return reservationRepo.findById(reservationid);
     }

     public void update(Reservation reservation, Integer reservationid) {
          Optional<Reservation> currentReservation = getUReservationById(reservationid);
          if (!currentReservation.isPresent()) {
               throw new InvalidIdException("Invalid id");
          }

          currentReservation.get().setPark(reservation.getPark());

          reservationRepo.save(currentReservation.get());
     }

     public void delete(Integer reservationId) {
          Optional<Reservation> currentReservation = getUReservationById(reservationId);

          if (!currentReservation.isPresent()) {
               throw new InvalidIdException("Invalid id");
          }

          reservationRepo.delete(currentReservation.get());
     }

     public ResponseEntity<?> getAllReservedPark(Integer facilityId, String startTime, String endTime) {
          List<Integer> count =  reservationRepo.findAllByFacilityIdAndTime(facilityId, startTime, endTime);
          return ResponseEntity.status(201).body(count);
     }


}
