package com.example.gasholder.entity;


public class Options {

    private String iconLayout;
    private String iconImageHref;
    private int[] iconImageSize;
    private int[] iconImageOffset;

    public Options(String iconLayout, String iconImageHref, int[] iconImageSize, int[] iconImageOffset) {
        this.iconLayout = iconLayout;
        this.iconImageHref = iconImageHref;
        this.iconImageSize = iconImageSize;
        this.iconImageOffset = iconImageOffset;
    }

    public String getIconLayout() {
        return iconLayout;
    }

    public void setIconLayout(String iconLayout) {
        this.iconLayout = iconLayout;
    }

    public String getIconImageHref() {
        return iconImageHref;
    }

    public void setIconImageHref(String iconImageHref) {
        this.iconImageHref = iconImageHref;
    }

    public int[] getIconImageSize() {
        return iconImageSize;
    }

    public void setIconImageSize(int[] iconImageSize) {
        this.iconImageSize = iconImageSize;
    }

    public int[] getIconImageOffset() {
        return iconImageOffset;
    }

    public void setIconImageOffset(int[] iconImageOffset) {
        this.iconImageOffset = iconImageOffset;
    }
}
