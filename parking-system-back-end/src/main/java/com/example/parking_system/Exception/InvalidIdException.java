package com.example.parking_system.Exception;

public class InvalidIdException extends RuntimeException{
    public InvalidIdException(String message) {
        super(message);
    }
}
