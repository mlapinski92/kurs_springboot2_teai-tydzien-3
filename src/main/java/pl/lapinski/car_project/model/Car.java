package pl.lapinski.car_project.model;

import org.springframework.hateoas.RepresentationModel;

public class Car extends RepresentationModel {

    private long id;
    private String mark;
    private String model;
    private String colour;

    public Car(long id, String mark, String model, String colour) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.colour = colour;
    }

    public Car() {
    }

    public void modifyCar(Car car) {
        String newMark = car.getMark();
        String newModel = car.getModel();
        String newColor = car.getColour();

        if (newMark != null) {
            this.mark = newMark;
        }
        if (newModel != null) {
            this.model = newModel;
        }
        if (newColor != null) {
            this.colour = newColor;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
