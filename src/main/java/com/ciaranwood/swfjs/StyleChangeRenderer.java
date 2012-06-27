package com.ciaranwood.swfjs;

import com.ciaranwood.swfjs.model.Point;
import com.ciaranwood.vultan.types.Shape;

public class StyleChangeRenderer implements Renderer {

    private final Shape.StyleChangeRecord record;

    public StyleChangeRenderer(Shape.StyleChangeRecord record) {
        this.record = record;
    }

    public Point render(Canvas canvas, Point currentPosition) {
        if(record.stateMoveTo) {
            Point newPosition = new Point(record.moveDeltaX.getPixels(), record.moveDeltaY.getPixels());
            canvas.moveTo(newPosition.getX(), newPosition.getY());
            return newPosition;
        } else {
            return currentPosition;
        }
    }
}
