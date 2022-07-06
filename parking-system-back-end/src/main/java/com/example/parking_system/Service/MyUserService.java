package com.example.parking_system.Service;

import com.example.parking_system.Exception.InvalidIdException;
import com.example.parking_system.Repo.MyUserRepo;
import com.example.parking_system.Repo.ReservationRepo;
import com.example.parking_system.model.MyUser;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class MyUserService {
     private final MyUserRepo myUserRepo;
     private final ReservationRepo reservationRepo;

     public List<MyUser> getUsers() {
          return myUserRepo.findAll();
     }

     public MyUser addUser(MyUser myUser) {
          return myUserRepo.save(myUser);
     }

     public Optional<MyUser> getUserById(Integer userid) {
          return myUserRepo.findById(userid);
     }

     public MyUser update(MyUser myUser, Integer userid) {
          Optional<MyUser> currentUser = getUserById(userid);
          if (!currentUser.isPresent()) {
               throw new InvalidIdException("Invalid id");
          }

          currentUser.get().setPassword(myUser.getPassword());

          currentUser.get().setEmail(myUser.getEmail());
          currentUser.get().setPhoneNumber(myUser.getPhoneNumber());
          currentUser.get().setRole(myUser.getRole());
          currentUser.get().setName(myUser.getName());

          return myUserRepo.save(currentUser.get());
     }

     public void delete(Integer userid) {
          Optional<MyUser> currentUser = getUserById(userid);
          if (!currentUser.isPresent()) {
               throw new InvalidIdException("Invalid id");
          }
          myUserRepo.delete(currentUser.get());
     }
     public Optional<MyUser> getCustomerByEmailAndPassword(String email , String password) {
          return myUserRepo.findByEmailAndPassword(email,password);
     }


}
