package com.ciaranwood.swfjs;

import java.math.BigDecimal;

public interface Canvas {

    void translate(BigDecimal x, BigDecimal y);

    void transform(int scaleX, int rotateSkew1, int rotateSkew0, int scaleY, int translateX, int translateY);

    void setFillStyle();

    void setLineStyle();

    void lineTo(BigDecimal x, BigDecimal y);

    void curveTo(BigDecimal controlX, BigDecimal controlY, BigDecimal anchorX, BigDecimal anchorY);

    void moveTo(BigDecimal x, BigDecimal y);

    void startPath();

    void closePath();

    void stroke();

    void save();

    void restore();
}
