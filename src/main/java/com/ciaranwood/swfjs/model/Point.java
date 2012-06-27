package com.ciaranwood.swfjs.model;

import java.math.BigDecimal;

public class Point {

    private final BigDecimal x;
    private final BigDecimal y;

    public Point(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public Point add(BigDecimal x, BigDecimal y) {
        return new Point(this.x.add(x), this.y.add(y));
    }

    public Point copy() {
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return "Point(" +
                x +
                "," + y +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x.compareTo(point.x) != 0) return false;
        if (y.compareTo(point.y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }
}
