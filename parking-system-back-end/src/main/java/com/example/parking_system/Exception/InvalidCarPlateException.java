package com.example.parking_system.Exception;

public class InvalidCarPlateException extends RuntimeException{
    public InvalidCarPlateException(String message) {
        super(message);
    }
}
