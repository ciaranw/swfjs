package com.ciaranwood.swfjs.model;

public class ChangeLineStyleCommand implements ShapeCommand {

    private final Boolean clear;
    private final Integer lineStyle;

    public ChangeLineStyleCommand() {
        this.clear = true;
        this.lineStyle = null;
    }

    public ChangeLineStyleCommand(Integer lineStyle) {
        this.clear = false;
        this.lineStyle = lineStyle;
    }

    public ShapeCommandType getType() {
        return ShapeCommandType.LINE;
    }

    public Boolean getClear() {
        return clear;
    }

    public Integer getLineStyle() {
        return lineStyle;
    }
}
