package com.example.gasholder.entity;

public class Properties {

    private String hintContent;
    private String iconCaption;
    private String iconContent;

    public String getIconContent() {
        return iconContent;
    }

    public void setIconContent(String iconContent) {
        this.iconContent = iconContent;
    }

    public String getIconCaption() {
        return iconCaption;
    }

    public void setIconCaption(String iconCaption) {
        this.iconCaption = iconCaption;
    }

    public Properties() {
    }

    public Properties(String hintContent) {
        this.hintContent = hintContent;
    }

    public String getHintContent() {
        return hintContent;
    }

    public void setHintContent(String hintContent) {
        this.hintContent = hintContent;
    }
}
