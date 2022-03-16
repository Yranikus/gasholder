package com.example.gasholder.entity;

public class CityPoint extends Point{

    private String type;

    public CityPoint() {
    }

    public CityPoint(String name, Double latitude, Double longitude, String type) {
        super(name, latitude, longitude);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
