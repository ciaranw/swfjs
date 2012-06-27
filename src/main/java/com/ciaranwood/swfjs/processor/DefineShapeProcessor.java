package com.ciaranwood.swfjs.processor;

import com.ciaranwood.swfjs.model.AbstractStyledShapeCommand;
import com.ciaranwood.swfjs.model.DefineStylesCommand;
import com.ciaranwood.swfjs.model.Shape;
import com.ciaranwood.swfjs.model.ShapeCommand;
import com.ciaranwood.swfjs.shape.CommandWithStartPoint;
import com.ciaranwood.vultan.tags.DefineShape;
import com.ciaranwood.vultan.types.ShapeWithStyle1;

import java.util.ArrayList;
import java.util.List;

public class DefineShapeProcessor {

    private final RectBoundsProcessor rectBoundsProcessor = new RectBoundsProcessor();

    public Shape process(DefineShape tag) {
        final Shape shape = new Shape(tag.getCharacterId(), rectBoundsProcessor.process(tag.shapeBounds));

        ShapeWithStyle1 shapes = tag.getShapes();
        ShapeRecordsProcessor recordsProcessor = new ShapeRecordsProcessor(shapes.fillStyles, shapes.lineStyleArray);

        final List<CommandWithStartPoint> commands = new ArrayList<CommandWithStartPoint>();

        DefineStylesCommand styles = recordsProcessor.process(shapes.shape.shapeRecords, new ShapeRecordsProcessor.CommandHandler() {
            public void onShapeCommand(CommandWithStartPoint command) {
                commands.add(command);
            }
        });

        new ShapeProcessor().process(shape, commands);

        shape.addStyles(styles);

        return shape;
    }

}
