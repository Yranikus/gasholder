package com.example.gasholder.entity;

public class WaterPoint extends Point{

    private String type;

    public WaterPoint(){
    }

    public WaterPoint(String name, Double latitude, Double longitude, String type) {
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
