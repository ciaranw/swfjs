package com.ciaranwood.swfjs;

import com.ciaranwood.swfjs.model.Point;
import com.ciaranwood.vultan.types.CurvedEdgeRecord;

public class CurvedEdgeRenderer implements Renderer {

    private final CurvedEdgeRecord record;

    public CurvedEdgeRenderer(CurvedEdgeRecord record) {
        this.record = record;
    }

    public Point render(Canvas canvas, Point currentPosition) {
        Point control = currentPosition.add(record.controlDeltaX.getPixels(), record.controlDeltaY.getPixels());
        Point anchor  = control.add(record.anchorDeltaX.getPixels(), record.anchorDeltaY.getPixels());
        canvas.curveTo(control.getX(), control.getY(), anchor.getX(), anchor.getY());

        return anchor;
    }
}
