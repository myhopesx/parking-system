package com.example.parking_system.Repo;

import com.example.parking_system.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation,Integer> {

    @Query(value = "SELECT id FROM parking.reservation where my_user_id=?1 AND start_time < now() AND now()< end_time",nativeQuery = true)
    Integer checkReservationTime(Integer userID);// checks if there is a valid reservation

    @Query(value = "SELECT * FROM parking_system.reservation where facility_id=?",nativeQuery = true)
    List<Reservation> findAllByFacilityId(Integer id);

    @Query(value = "SELECT park.park_number FROM parking_system.reservation \n" +
            "inner join parking_system.park on park.id = reservation.park_id\n" +
            "where reservation.facility_id=? And reservation.start_time=? And reservation.end_time=?",nativeQuery= true)
    List<Integer> findAllByFacilityIdAndTime(Integer id , String startTime , String endTime);



}
