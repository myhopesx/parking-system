package com.example.parking_system.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FacilityAdmin implements UserDetails {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer Id;

     @OneToMany(mappedBy = "facilityAdmin", cascade = CascadeType.ALL)
     private Set<Facility> facilities;

     @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
     @JoinColumn(name = "user_id")
     private MyUser myUser;

     @Override
     @JsonIgnore
     public Collection<? extends GrantedAuthority> getAuthorities() {
          return myUser.getAuthorities();
     }

     @Override
     @JsonIgnore
     public String getPassword() {
          return myUser.getPassword();
     }

     @Override
     @JsonIgnore
     public String getUsername() {
          return myUser.getEmail();
     }

     @Override
     @JsonIgnore
     public boolean isAccountNonExpired() {
          return true;
     }

     @Override
     @JsonIgnore
     public boolean isAccountNonLocked() {
          return true;
     }

     @Override
     @JsonIgnore
     public boolean isCredentialsNonExpired() {
          return true;
     }

     @Override
     @JsonIgnore
     public boolean isEnabled() {
          return true;
     }
}
