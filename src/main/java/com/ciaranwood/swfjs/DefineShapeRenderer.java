package com.ciaranwood.swfjs;

import com.ciaranwood.swfjs.model.Point;
import com.ciaranwood.vultan.tags.DefineShape;
import com.ciaranwood.vultan.types.*;

public class DefineShapeRenderer implements Renderer {

    private final DefineShape tag;

    public DefineShapeRenderer(DefineShape tag) {
        this.tag = tag;
    }

    public Point render(Canvas canvas, Point currentPosition) {
        Point position = currentPosition.copy();
        Rect bounds = tag.shapeBounds;
        canvas.save();
        //canvas.translate(bounds.xMin.getPixels().abs(), bounds.yMin.getPixels().abs());
        canvas.startPath();
        for(ShapeRecord record : tag.getShapes().shape.shapeRecords) {
            Renderer renderer;
            if(record instanceof Shape.StyleChangeRecord) {
                renderer = new StyleChangeRenderer((Shape.StyleChangeRecord) record);
            } else if(record instanceof StraightEdgeRecord) {
                renderer = new StraightEdgeRenderer((StraightEdgeRecord) record);
            } else if(record instanceof CurvedEdgeRecord) {
                renderer = new CurvedEdgeRenderer((CurvedEdgeRecord) record);
            } else {
                throw new IllegalArgumentException("unknown type " + record.getClass());
            }

            position = renderer.render(canvas, position);
        }
        canvas.closePath();
        canvas.stroke();
        canvas.restore();

        return position;
    }
}
