package com.example.parking_system.model;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Login {
    private String email;
    private String password;
}