package com.ciaranwood.swfjs.shape;

import com.ciaranwood.swfjs.model.CurvedEdgeShapeCommand;
import com.ciaranwood.swfjs.model.Point;
import com.ciaranwood.swfjs.model.ShapeCommand;
import com.ciaranwood.swfjs.model.StraightEdgeShapeCommand;

public class CommandWithStartPoint {

    private ShapeCommand command;
    private Point start;

    public CommandWithStartPoint(ShapeCommand command, Point start) {
        this.command = command;
        this.start = start;
    }

    public ShapeCommand getCommand() {
        return command;
    }

    public Point getStart() {
        return start;
    }

    public CommandWithStartPoint flip() {
        Point newStart;
        ShapeCommand newCommand;
        if(command instanceof StraightEdgeShapeCommand) {
            StraightEdgeShapeCommand sCommand = (StraightEdgeShapeCommand) command;
            newCommand = new StraightEdgeShapeCommand(start, sCommand.getFillStyle1(), sCommand.getFillStyle0(), sCommand.getLineStyle());
            newStart = sCommand.getTo();
        } else if(command instanceof CurvedEdgeShapeCommand) {
            CurvedEdgeShapeCommand cCommand = (CurvedEdgeShapeCommand) command;
            newCommand = new CurvedEdgeShapeCommand(cCommand.getControl(), start, cCommand.getFillStyle1(), cCommand.getFillStyle0(), cCommand.getLineStyle());
            newStart = cCommand.getAnchor();
        } else {
            throw new IllegalStateException("cannot flip " + command);
        }

        return new CommandWithStartPoint(newCommand, newStart);
    }

    @Override
    public String toString() {
        return "CommandWithStartPoint{" +
                "command=" + command.getType() +
                ", start=" + start +
                '}';
    }
}
