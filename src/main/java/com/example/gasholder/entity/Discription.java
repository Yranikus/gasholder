package com.example.gasholder.entity;

public class Discription {

    private String field;
    private String area;
    private String AGZU;
    private String workshop;
    private String city;
    private double distance;
    private String direction;
    private String reservior;
    private double reservior_distance;
    private String reservior_direction;


    public Discription() {
    }

    public Discription(String field, String area, String AGZU,
                       String workshop, String city, double distance,
                       String direction, String reservior, double reservior_distance,
                       String reservior_direction) {
        this.field = field;
        this.area = area;
        this.AGZU = AGZU;
        this.workshop = workshop;
        this.city = city;
        this.distance = distance;
        this.direction = direction;
        this.reservior = reservior;
        this.reservior_distance = reservior_distance;
        this.reservior_direction = reservior_direction;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCity() {
        return city;
    }

    public String getReservior() {
        return reservior;
    }

    public void setReservior(String reservior) {
        this.reservior = reservior;
    }

    public double getReservior_distance() {
        return reservior_distance;
    }

    public void setReservior_distance(double reservior_distance) {
        this.reservior_distance = reservior_distance;
    }

    public String getReservior_direction() {
        return reservior_direction;
    }

    public void setReservior_direction(String reservior_direction) {
        this.reservior_direction = reservior_direction;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAGZU() {
        return AGZU;
    }

    public void setAGZU(String AGZU) {
        this.AGZU = AGZU;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }
}
