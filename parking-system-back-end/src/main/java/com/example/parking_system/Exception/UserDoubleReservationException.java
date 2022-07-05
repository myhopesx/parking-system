package com.example.parking_system.Exception;

public class UserDoubleReservationException extends RuntimeException{
    public UserDoubleReservationException(String message) {
        super(message);
    }
}
