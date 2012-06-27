package com.ciaranwood.swfjs.model;

import java.util.ArrayList;
import java.util.List;

public class Shape implements CommandReceiver {

    private final Integer characterId;
    private final Bounds bounds;
    private final List<ShapeCommand> commands;

    public Shape(Integer characterId, Bounds bounds) {
        this.characterId = characterId;
        this.bounds = bounds;
        this.commands = new ArrayList<ShapeCommand>();
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public List<ShapeCommand> getCommands() {
        return commands;
    }

    public void addCommand(ShapeCommand command) {
        commands.add(command);
    }

    public void addStyles(DefineStylesCommand command) {
        commands.add(0, command);
    }

    public TagType getType() {
        return TagType.SHAPE;
    }
}
