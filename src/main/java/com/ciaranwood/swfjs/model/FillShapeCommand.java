package com.ciaranwood.swfjs.model;

public class FillShapeCommand implements ShapeCommand {

    public ShapeCommandType getType() {
        return ShapeCommandType.FILL;
    }
}
