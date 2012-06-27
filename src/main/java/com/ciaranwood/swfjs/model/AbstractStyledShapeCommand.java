package com.ciaranwood.swfjs.model;

public abstract class AbstractStyledShapeCommand implements ShapeCommand {

    private final Integer fillStyle0;
    private final Integer fillStyle1;
    private final Integer lineStyle;

    protected AbstractStyledShapeCommand(Integer fillStyle0, Integer fillStyle1, Integer lineStyle) {
        this.fillStyle0 = fillStyle0;
        this.fillStyle1 = fillStyle1;
        this.lineStyle = lineStyle;
    }

    public Integer getFillStyle0() {
        return fillStyle0;
    }

    public Integer getFillStyle1() {
        return fillStyle1;
    }

    public Integer getLineStyle() {
        return lineStyle;
    }
}
