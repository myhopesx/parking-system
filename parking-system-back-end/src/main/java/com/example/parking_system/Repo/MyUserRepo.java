package com.example.parking_system.Repo;
import com.example.parking_system.model.MyUser;
import com.example.parking_system.model.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface MyUserRepo extends JpaRepository<MyUser, Integer> {
     Optional<MyUser> findByEmail(String email);
     Optional<MyUser> findByEmailAndPassword(String email,String password);
}