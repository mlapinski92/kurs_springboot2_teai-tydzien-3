package pl.lapinski.car_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lapinski.car_project.model.Car;
import pl.lapinski.car_project.service.CarService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/cars", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CarApi {

    private CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Car>> getCars() {
        List<Car> carList = carService.getCarList();
        if (!carList.isEmpty()) {
            carList.forEach(car -> car.add(linkTo(CarApi.class).slash(car.getId()).withSelfRel()));
            Link link = linkTo(CarApi.class).withSelfRel();
            CollectionModel<Car> collectionModel = new CollectionModel<>(carList, link);
            return new ResponseEntity<>(collectionModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Car>> getCarById(@PathVariable long id) {
        Link link = linkTo(CarApi.class).slash(id).withSelfRel();
        Optional<Car> carById = carService.getCarById(id);
        EntityModel<Car> carEntityModel = new EntityModel<>(carById.get(), link);
        if (carById.isPresent()) {
            return new ResponseEntity<>(carEntityModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/colour/{colour}")
    public ResponseEntity<List<Car>> getCarsByColour(@PathVariable String colour) {
        List<Car> carsByColour = carService.getCarsByColour(colour);
        if (carsByColour.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(carsByColour, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car car) {
        boolean add = carService.addCar(car);
        if (add) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity modCar(@RequestBody Car newCar) {
        boolean modCar = carService.modifyCar(newCar);
        if (modCar) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //
    @PatchMapping
    public ResponseEntity modCarParameter(@RequestBody Car modCar) {
        boolean modified = carService.modifyCarParameters(modCar);
        if (modified) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable long id) {
        boolean deleted = carService.removeCar(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
