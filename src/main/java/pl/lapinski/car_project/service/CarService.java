package pl.lapinski.car_project.service;

import pl.lapinski.car_project.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getCarList();
    Optional<Car> getCarById(long id);
    List<Car> getCarsByColour(String color);
    boolean addCar(Car car);
    boolean modifyCar(Car car);
    boolean modifyCarParameters(Car car);
    boolean removeCar(long id);
}
