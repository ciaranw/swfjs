package com.ciaranwood.swfjs.model;

public class ShapeFillStyle {

    private final String color;

    public ShapeFillStyle(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShapeFillStyle that = (ShapeFillStyle) o;

        if (!color.equals(that.color)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }
}
