package com.ciaranwood.swfjs.model;

public class RemoveFrameAction implements FrameAction {

    private final Integer depth;

    public RemoveFrameAction(Integer depth) {
        this.depth = depth;
    }

    public FrameActionType getType() {
        return FrameActionType.REMOVE;
    }

    public Integer getDepth() {
        return depth;
    }
}
