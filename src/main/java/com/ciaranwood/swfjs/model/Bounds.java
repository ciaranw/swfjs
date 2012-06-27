package com.ciaranwood.swfjs.model;

public class Bounds {

    private final Point min;
    private final Point max;

    public Bounds(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    public Point getMin() {
        return min;
    }

    public Point getMax() {
        return max;
    }
}
