package com.ciaranwood.swfjs.model;

import java.math.BigDecimal;

public class ShapeLineStyle {

    private final String color;
    private final BigDecimal width;

    public ShapeLineStyle(BigDecimal width, String color) {
        this.width = width;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShapeLineStyle that = (ShapeLineStyle) o;

        if (!color.equals(that.color)) return false;
        if (!width.equals(that.width)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + width.hashCode();
        return result;
    }
}
