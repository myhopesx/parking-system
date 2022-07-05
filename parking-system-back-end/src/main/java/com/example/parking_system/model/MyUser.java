package com.example.parking_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Data @Entity
public class MyUser implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotEmpty(message = "Password is required ")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Phone number is required ")
    private String phoneNumber;

    @NotEmpty(message = "Name is required ")
    private String name;

    @Email @NotEmpty(message = "Email is required ")
    private String email;

    @NotEmpty(message = "Role should be customer, owner or security") @Pattern(regexp = "customer|owner|security|admin")
    private String role;

    @OneToOne(mappedBy = "myUser", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private Customer customer;

    @OneToOne(mappedBy = "myUser", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private FacilityAdmin facilityAdmin;

    @OneToOne(mappedBy = "myUser", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private SecurityMan securityMan;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return null;
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
