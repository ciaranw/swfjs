package com.ciaranwood.swfjs.model;

public class MoveShapeCommand implements ShapeCommand, PositionalShapeCommand {

    private final Point to;

    public MoveShapeCommand(Point to) {
        this.to = to;
    }

    public ShapeCommandType getType() {
        return ShapeCommandType.MOVE;
    }

    public Point getTo() {
        return to;
    }

    public Point getEnd() {
        return to;
    }
}
