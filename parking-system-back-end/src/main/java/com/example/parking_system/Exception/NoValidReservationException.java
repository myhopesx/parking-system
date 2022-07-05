package com.example.parking_system.Exception;

public class NoValidReservationException extends RuntimeException{
    public NoValidReservationException(String message) {
        super(message);
    }
}
