package com.example.gasholder.entity;

public class Discription {

    private String field;
    private String area;
    private String AGZU;
    private String workshop;


    public Discription() {
    }

    public Discription(String field, String area, String AGZU, String workshop) {
        this.field = field;
        this.area = area;
        this.AGZU = AGZU;
        this.workshop = workshop;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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
