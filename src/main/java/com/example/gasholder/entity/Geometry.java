package com.example.gasholder.entity;

public class Geometry {

    private String type = "Point";
    private double[] coordinates = new double[2];


    public Geometry() {
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double coordinate, int i) {
        this.coordinates[i] = coordinate;
    }
}
