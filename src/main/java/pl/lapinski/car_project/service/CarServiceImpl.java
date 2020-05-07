package pl.lapinski.car_project.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.lapinski.car_project.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private List<Car> carList;

    public CarServiceImpl() {
        carList = new ArrayList<>();
        carList.add(new Car(1L, "Audi", "A3", "black"));
        carList.add(new Car(2L, "Mercedes", "Sprinter", "black"));
        carList.add(new Car(3L, "BMW", "1", "white"));
        carList.add(new Car(4L, "Honda", "Civic VIII", "Orange"));
    }

    @Override
    public List<Car> getCarList() {
        return carList;
    }

    @Override
    public Optional<Car> getCarById(long id) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        return first;
    }

    @Override
    public List<Car> getCarsByColour(String colour) {
        List<Car> colouredCarList = carList.stream().filter(car -> car.getColour().equals(colour))
                .collect(Collectors.toList());
        return colouredCarList;
    }

    @Override
    public boolean addCar(Car car) {
        carList.add(car);
        return true;
    }

    @Override
    public boolean modifyCar(Car modifyCar) {
        Optional<Car> modifiedCar = carList.stream().filter(car -> car.getId() == modifyCar.getId()).findFirst();
        if (modifiedCar.isPresent()) {
            carList.remove(modifiedCar.get());
            carList.add(modifyCar);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyCarParameters(Car modCar) {
        Optional<Car> optionalCar = carList.stream().filter(car -> car.getId() == modCar.getId()).findFirst();
        if (optionalCar.isPresent()) {
            if (modCar.getMark() != null) {
                optionalCar.get().setMark(modCar.getMark());
            }
            if (modCar.getModel() != null) {
                optionalCar.get().setModel(modCar.getModel());
            }
            if (modCar.getColour() != null) {
                optionalCar.get().setColour(modCar.getColour());
            }
            return true;
        }
        else return false;
    }

    @Override
    public boolean removeCar(long id) {
        Optional<Car> carOptional = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (carOptional.isPresent()) {
            carList.remove(carOptional.get());
            return true;
        }
        return false;
    }
}

