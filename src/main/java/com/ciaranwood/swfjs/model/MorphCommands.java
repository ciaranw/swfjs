package com.ciaranwood.swfjs.model;

import java.util.ArrayList;
import java.util.List;

public class MorphCommands implements CommandReceiver {

    private final Bounds bounds;
    private final List<ShapeCommand> commands;

    public MorphCommands(Bounds bounds) {
        this.bounds = bounds;
        this.commands = new ArrayList<ShapeCommand>();
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void addStyles(DefineStylesCommand styles) {
        commands.add(0, styles);
    }

    public List<ShapeCommand> getCommands() {
        return commands;
    }

    public void addCommand(ShapeCommand command) {
        commands.add(command);
    }
}
