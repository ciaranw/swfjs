package com.ciaranwood.swfjs.model;

public class Morph {

    private final Integer characterId;
    private final MorphCommands start;
    private final MorphCommands end;

    public Morph(Integer characterId, MorphCommands start, MorphCommands end) {
        this.characterId = characterId;
        this.start = start;
        this.end = end;
    }

    public TagType getType() {
        return TagType.MORPH;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public MorphCommands getStart() {
        return start;
    }

    public MorphCommands getEnd() {
        return end;
    }
}
