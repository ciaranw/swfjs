package com.ciaranwood.swfjs.processor;

import com.ciaranwood.swfjs.model.Bounds;
import com.ciaranwood.swfjs.model.Point;
import com.ciaranwood.vultan.types.Rect;

public class RectBoundsProcessor {

    public Bounds process(Rect rect) {
        Point min = new Point(rect.xMin.getPixels(), rect.yMin.getPixels());
        Point max = new Point(rect.xMax.getPixels(), rect.yMax.getPixels());

        return new Bounds(min, max);
    }
}
