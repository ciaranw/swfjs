package com.ciaranwood.swfjs.model;

import java.math.BigDecimal;

public class ReplaceFrameAction implements FrameAction {

    public final Integer depth;
    public final Integer character;
    public final BigDecimal[] matrix;

    public ReplaceFrameAction(Integer depth, Integer character, BigDecimal[] matrix) {
        this.depth = depth;
        this.character = character;
        this.matrix = matrix;
    }

    public FrameActionType getType() {
        return FrameActionType.REPLACE;
    }

    public Integer getDepth() {
        return depth;
    }

    public Integer getCharacter() {
        return character;
    }

    public BigDecimal[] getMatrix() {
        return matrix;
    }
}
