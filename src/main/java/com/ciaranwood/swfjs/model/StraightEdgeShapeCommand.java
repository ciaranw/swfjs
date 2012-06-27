package com.ciaranwood.swfjs.model;

public class StraightEdgeShapeCommand extends AbstractStyledShapeCommand implements PositionalShapeCommand {

    private final Point to;

    public StraightEdgeShapeCommand(Point to, Integer fillStyle0, Integer fillStyle1, Integer lineStyle) {
        super(fillStyle0, fillStyle1, lineStyle);
        this.to = to;
    }

    public ShapeCommandType getType() {
        return ShapeCommandType.STRAIGHT;
    }

    public Point getTo() {
        return to;
    }

    public Point getEnd() {
        return to;
    }
}
