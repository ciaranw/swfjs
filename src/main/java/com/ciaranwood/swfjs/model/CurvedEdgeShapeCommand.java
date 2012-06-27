package com.ciaranwood.swfjs.model;

public class CurvedEdgeShapeCommand extends AbstractStyledShapeCommand implements PositionalShapeCommand {

    private final Point control;
    private final Point anchor;

    public CurvedEdgeShapeCommand(Point control, Point anchor, Integer fillStyle0, Integer fillStyle1, Integer lineStyle) {
        super(fillStyle0, fillStyle1, lineStyle);
        this.control = control;
        this.anchor = anchor;
    }

    public ShapeCommandType getType() {
        return ShapeCommandType.CURVED;
    }

    public Point getControl() {
        return control;
    }

    public Point getAnchor() {
        return anchor;
    }

    public Point getEnd() {
        return anchor;
    }
}
