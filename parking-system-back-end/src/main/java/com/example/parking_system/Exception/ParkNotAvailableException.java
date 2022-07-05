package com.example.parking_system.Exception;

public class ParkNotAvailableException extends RuntimeException{
    public ParkNotAvailableException(String message) {
        super(message);
    }
}
