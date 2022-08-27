package com.example.gasholder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PointJs {

    private String type = "Feature";
    private int id;
    private Geometry geometry;
    private Properties properties;
    private Options options;
    @JsonIgnore
    private String name;

    public PointJs(int id, Geometry geometry, Properties properties, Options options) {
        this.id = id;
        this.geometry = geometry;
        this.properties = properties;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
