package com.example.parking_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Facility {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer Id;

     @NotEmpty(message = "name is required ")
     private String name;

     @JsonFormat(pattern = "HH:mm:ss")
     @NotNull(message = "start time is required ")
     private String startTime;

     @JsonFormat(pattern = "HH:mm:ss")
     @NotNull(message = "end time is required ")
     private String endTime;

     @NotEmpty(message = "logo is required ")
     private String logo;

     @NotNull(message = "address is required ")
     private String address;

     @NotEmpty(message = "description is required ")
     private String description;

     @NotEmpty(message = "contactInfo is required ")
     private String contactInfo;

     private Boolean isActive;

     @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
     @JsonIgnore
     private Set<Park> parks;

     @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
     @JsonIgnore
     private Set<Reservation> reservations;

     @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL)
     private Set<SecurityMan> securityMEN;

     @ManyToOne(fetch = FetchType.LAZY)
     @JsonIgnore
     @JoinColumn(name = "facilityAdmin_id")
     private FacilityAdmin facilityAdmin;

}
