package com.example.parking_system.Service;

import com.example.parking_system.Exception.InvalidIdException;
import com.example.parking_system.Repo.CarRepo;
import com.example.parking_system.model.Customer;
import com.example.parking_system.model.Car;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

     private final CarRepo carRepo;
     private final CustomerServices customerServices;

     public List<Car> getCars() {
          return carRepo.findAll();
     }

     public Car addCar(Car car, Integer customer_id) {
          Optional<Customer> customer = customerServices.getCustomerById(customer_id);
          if (!customer.isPresent()) {
               return null;
          }

          car.setCustomer(customer.get());
          return carRepo.save(car);
     }

     public Optional<Car> getCarById(Integer carid) {
          return carRepo.findById(carid);
     }

     public void update(Car car, Integer carid) {
          Optional<Car> currentCar = getCarById(carid);
          if (!currentCar.isPresent()) {
               throw new InvalidIdException("Invalid id");
          }

          currentCar.get().setPlate(car.getPlate());
          currentCar.get().setName(car.getName());

          carRepo.save(currentCar.get());
     }

     public void delete(Integer carid) {
          Optional<Car> currentCar = getCarById(carid);
          if (!currentCar.isPresent()) {
               throw new InvalidIdException("Invalid id");
          }

          carRepo.delete(currentCar.get());
     }

//     public List<Car> getCarsByCustomerId(Integer customer_id) {
//          return carRepo.findAllByCustomer_Id(customer_id);
//     }
}
