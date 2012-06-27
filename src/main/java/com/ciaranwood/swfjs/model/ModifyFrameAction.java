package com.ciaranwood.swfjs.model;

import java.math.BigDecimal;

public class ModifyFrameAction implements FrameAction {

    private final Integer depth;
    private final BigDecimal[] matrix;

    public ModifyFrameAction(Integer depth, BigDecimal[] matrix) {
        this.depth = depth;
        this.matrix = matrix;
    }

    public FrameActionType getType() {
        return FrameActionType.MODIFY;
    }

    public Integer getDepth() {
        return depth;
    }

    public BigDecimal[] getMatrix() {
        return matrix;
    }
}
