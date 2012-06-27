package com.ciaranwood.swfjs;

import com.ciaranwood.swfjs.model.Point;

public interface Renderer {

    Point render(Canvas canvas, Point currentPosition);

}
