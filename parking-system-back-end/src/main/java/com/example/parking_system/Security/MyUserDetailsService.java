package com.example.parking_system.Security;

import com.example.parking_system.Repo.MyUserRepo;
import com.example.parking_system.model.MyUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

     private final MyUserRepo myUserRepo;

     public MyUserDetailsService(MyUserRepo myUserRepo) {
          this.myUserRepo = myUserRepo;
     }

     @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
          Optional<MyUser> user = myUserRepo.findByEmail(email);

          if (!user.isPresent()) {
               throw new UsernameNotFoundException("User name not found !");
          }

          List<SimpleGrantedAuthority> authorities = new ArrayList<>();
          authorities.add(new SimpleGrantedAuthority(user.get().getRole()));


          return new User(user.get().getEmail(), user.get().getPassword(), authorities);
     }


}
