package com.ciaranwood.swfjs;

import com.ciaranwood.swfjs.model.Point;
import com.ciaranwood.vultan.types.StraightEdgeRecord;

import java.math.BigDecimal;

public class StraightEdgeRenderer implements Renderer {

    private final StraightEdgeRecord record;

    public StraightEdgeRenderer(StraightEdgeRecord record) {
        this.record = record;
    }

    public Point render(Canvas canvas, Point currentPosition) {
        BigDecimal deltaX = BigDecimal.ZERO;
        BigDecimal deltaY = BigDecimal.ZERO;
        if(record.generalLineFlag) {
            deltaX = record.generalDeltaX.getPixels();
            deltaY = record.generalDeltaY.getPixels();
        } else {
            if(record.vertLineFlag) {
                deltaY = record.verticalDeltaY.getPixels();
            } else {
                deltaX = record.horizontalDeltaX.getPixels();
            }
        }

        Point newPosition = currentPosition.add(deltaX, deltaY);
        canvas.lineTo(newPosition.getX(), newPosition.getY());
        return newPosition;
    }
}
