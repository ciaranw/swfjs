package com.ciaranwood.swfjs.model;

import java.math.BigDecimal;

public class AddFrameAction implements FrameAction {

    public final Integer depth;
    public final Integer character;
    public final Boolean clipping;
    public final BigDecimal[] matrix;

    public AddFrameAction(Integer depth, Integer character, BigDecimal[] matrix, Boolean clipping) {
        this.depth = depth;
        this.character = character;
        this.matrix = matrix;
        if(clipping) {
            this.clipping = clipping;
        } else {
            this.clipping = null;
        }
    }

    public FrameActionType getType() {
        return FrameActionType.ADD;
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

    public Boolean getClipping() {
        return clipping;
    }
}
