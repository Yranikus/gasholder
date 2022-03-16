package com.example.gasholder.entity;

public class OilWellPoint extends Point{

    private String district;
    private String NearestCity;
    private String NearestBodyOfWater;

    public OilWellPoint() {
    }

    public OilWellPoint(String name, Double latitude, Double longitude, String district, String nearestCity, String nearestBodyOfWater) {
        super(name, latitude, longitude);
        this.district = district;
        this.NearestCity = nearestCity;
        this.NearestBodyOfWater = nearestBodyOfWater;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNearestCity() {
        return NearestCity;
    }

    public void setNearestCity(String nearestCity) {
        NearestCity = nearestCity;
    }

    public String getNearestBodyOfWater() {
        return NearestBodyOfWater;
    }

    public void setNearestBodyOfWater(String nearestBodyOfWater) {
        NearestBodyOfWater = nearestBodyOfWater;
    }
}
