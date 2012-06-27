package com.ciaranwood.swfjs.processor;

import com.ciaranwood.swfjs.model.*;
import com.ciaranwood.swfjs.shape.CommandWithStartPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ShapeProcessor {

    private Map<Integer, List<CommandWithStartPoint>> edgeMaps;

    public void process(CommandReceiver shape, List<CommandWithStartPoint> commands) {
        edgeMaps = new LinkedHashMap<Integer, List<CommandWithStartPoint>>();

        for(CommandWithStartPoint commandWithStartPoint : commands) {
            ShapeCommand command = commandWithStartPoint.getCommand();
            if(command instanceof AbstractStyledShapeCommand) {
                AbstractStyledShapeCommand sCommand = (AbstractStyledShapeCommand) command;

                Integer fillStyle0 = sCommand.getFillStyle0();
                if(fillStyle0 != null) {
                    addToEdgeMap(fillStyle0, commandWithStartPoint.flip());
                }

                Integer fillStyle1 = sCommand.getFillStyle1();
                if(fillStyle1 != null) {
                    addToEdgeMap(fillStyle1, commandWithStartPoint);
                }

                if(fillStyle0 == null && fillStyle1 == null) {
                    addToEdgeMap(-1, commandWithStartPoint);
                }
            }
        }

        for(Integer fillStyle : edgeMaps.keySet()) {
            List<CommandWithStartPoint> edgeMap = edgeMaps.get(fillStyle);
            Stack<CommandWithStartPoint> stack = new Stack<CommandWithStartPoint>();
            List<CommandWithStartPoint> reversed = new ArrayList<CommandWithStartPoint>(edgeMap);
            Collections.reverse(reversed);
            stack.addAll(reversed);



            while(!stack.isEmpty()) {
                Integer lineStyle = null;
                shape.addCommand(new ChangeLineStyleCommand());
                List<ShapeCommand> shapeCommands = reorderEdges(stack);
                for(ShapeCommand c : shapeCommands) {
                    Integer commandLineStyle = getLineStyle(c);
                    if (commandLineStyle == null) {
                        if(lineStyle != null) {
                            lineStyle = null;
                            shape.addCommand(new ChangeLineStyleCommand());
                        }
                    } else {
                        if(lineStyle == null) {
                            lineStyle = commandLineStyle;
                            shape.addCommand(new ChangeLineStyleCommand(commandLineStyle));
                        }
                    }
                    shape.addCommand(c);
                }

            }
            shape.addCommand(new FillShapeCommand());
        }

    }

    private Integer getLineStyle(ShapeCommand command) {
        if(command instanceof AbstractStyledShapeCommand) {
            AbstractStyledShapeCommand styled = (AbstractStyledShapeCommand) command;
            return styled.getLineStyle();
        } else {
            return null;
        }
    }

    private List<ShapeCommand> reorderEdges(Stack<CommandWithStartPoint> stack) {
        List<ShapeCommand> commands = new ArrayList<ShapeCommand>();
        boolean complete = false;
        CommandWithStartPoint command = stack.pop();
        commands.add(new MoveShapeCommand(command.getStart()));
        Point start = command.getStart();
        commands.add(command.getCommand());

        while(!(complete || stack.isEmpty())) {
            Point end = findEnd(command);
            CommandWithStartPoint next = removeNext(stack, end);
            if(next == null) {
                complete = true;
            } else {
                commands.add(next.getCommand());
                command = next;
                complete = findEnd(next).equals(start);
            }
        }

        return commands;
    }

    private CommandWithStartPoint removeNext(Stack<CommandWithStartPoint> stack, Point end) {
        Iterator<CommandWithStartPoint> iterator = stack.iterator();
        while(iterator.hasNext()) {
            CommandWithStartPoint command = iterator.next();
            if(command.getStart().equals(end)) {
                iterator.remove();
                return command;
            }
        }
        return null;
    }

    private Point findEnd(CommandWithStartPoint command) {
        ShapeCommand shapeCommand = command.getCommand();
        if(shapeCommand instanceof PositionalShapeCommand) {
            return ((PositionalShapeCommand) shapeCommand).getEnd();
        } else {
            throw new IllegalArgumentException("could not handle command " + shapeCommand);
        }
    }

    private void addToEdgeMap(Integer fillStyle0, CommandWithStartPoint sCommand) {
        List<CommandWithStartPoint> commands = edgeMaps.get(fillStyle0);
        if(commands == null) {
            commands = new ArrayList<CommandWithStartPoint>();
            edgeMaps.put(fillStyle0, commands);
        }

        commands.add(sCommand);
    }
}
