package com.example.gasholder.entity;

import java.util.ArrayList;

public class ArryOfPoints {

    private String type = "FeatureCollection";
    private ArrayList<PointJs> features;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<PointJs> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<PointJs> features) {
        this.features = features;
    }
}
