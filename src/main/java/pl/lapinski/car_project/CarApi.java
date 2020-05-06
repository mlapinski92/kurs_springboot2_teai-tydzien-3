package pl.lapinski.car_project;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/cars")
public class CarApi {

    private List<Car> carList;

    public CarApi() {
        this.carList = new ArrayList<>();
        carList.add(new Car(1L, "Audi", "A3", "black"));
        carList.add(new Car(2L, "Mercedes", "Sprinter", "black"));
        carList.add(new Car(3L, "BMW", "1", "white"));
        carList.add(new Car(4L, "Honda", "Civic VIII", "Orange"));
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Car>> getCars() {
        return new ResponseEntity<>(carList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/colour/{colour}")
    public ResponseEntity<List<Car>> getCarsByColour(@PathVariable String colour) {
        List<Car> optionalCarList = carList.stream().filter(car -> car.getColour().equals(colour))
                .collect(Collectors.toList());
        if (optionalCarList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalCarList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car car) {
        boolean add = carList.add(car);
        if (add) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity modCar(@RequestBody Car newCar) {

        Optional<Car> first = carList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();
        if (first.isPresent()) {
            carList.remove(first.get());
            carList.add(newCar);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping
    public ResponseEntity modCarParameter(@RequestBody Car modCar) {

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
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeVideo(@PathVariable long id) {
        Optional<Car> carOptional = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (carOptional.isPresent()) {
            carList.remove(carOptional.get());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
