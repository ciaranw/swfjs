package com.ciaranwood.swfjs.model;

public class RatioFrameAction implements FrameAction {

    private final Integer depth;
    private final Integer ratio;

    public RatioFrameAction(Integer depth, Integer ratio) {
        this.depth = depth;
        this.ratio = ratio;
    }

    public FrameActionType getType() {
        return FrameActionType.RATIO;
    }

    public Integer getDepth() {
        return depth;
    }

    public Integer getRatio() {
        return ratio;
    }
}
