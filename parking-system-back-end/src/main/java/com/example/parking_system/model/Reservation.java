package com.example.parking_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

//@SuppressWarnings("JpaAttributeTypeInspection")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reservation {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer Id;

     @NotEmpty
     @NotNull
     private String startTime;

     @NotEmpty
     @NotNull
     private String endTime;

     private Double amount = 0.0;

     @ManyToOne
     private Park park;

     @ManyToOne
     private Facility facility;

     @ManyToOne
     private Customer customer;

     @ManyToOne
     @JsonIgnore
     private Car car;
}
