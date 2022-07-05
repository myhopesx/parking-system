package com.example.parking_system.Advice;

import com.example.parking_system.Exception.*;
import com.example.parking_system.model.API;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {
//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<API> handleExeption(Exception exception){
//        System.out.println(exception.getMessage());
//        return ResponseEntity.status(500).body(new API("SERVER ERROR !",500));
//    }

    @ExceptionHandler(value = InvalidIdException.class)
    public ResponseEntity<API> handleDataIntegrity(InvalidIdException invalidIDException){
        String message=invalidIDException.getMessage();
        return ResponseEntity.status(400).body(new API(message,400));
    }

    @ExceptionHandler(value = InvalidCarPlateException.class)
    public ResponseEntity<API> handleDataIntegrity(InvalidCarPlateException invalidCarPlateException){
        String message=invalidCarPlateException.getMessage();
        return ResponseEntity.status(400).body(new API(message,400));
    }

    @ExceptionHandler(value = NoValidReservationException.class)
    public ResponseEntity<API> handleDataIntegrity(NoValidReservationException noValidReservationException){
        String message= noValidReservationException.getMessage();
        return ResponseEntity.status(400).body(new API(message,400));
    }
    @ExceptionHandler(value = ParkNotAvailableException.class)
    public ResponseEntity<API> handleDataIntegrity(ParkNotAvailableException parkNotAvailableException){
        String message= parkNotAvailableException.getMessage();
        return ResponseEntity.status(400).body(new API(message,400));
    }
    @ExceptionHandler(value = UserDoubleReservationException.class)
    public ResponseEntity<API> handleDataIntegrity(UserDoubleReservationException userDoubleReservationException){
        String message= userDoubleReservationException.getMessage();
        return ResponseEntity.status(400).body(new API(message,400));
    }


}
