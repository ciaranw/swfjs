package com.ciaranwood.swfjs.model;

import java.math.BigDecimal;

public class Header {

    private final BigDecimal frameRate;
    private final Bounds bounds;

    public Header(BigDecimal frameRate, Bounds bounds) {
        this.frameRate = frameRate;
        this.bounds = bounds;
    }

    public BigDecimal getFrameRate() {
        return frameRate;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public TagType getType() {
        return TagType.HEADER;
    }
}
