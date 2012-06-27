package com.ciaranwood.swfjs.processor;

import com.ciaranwood.swfjs.model.*;
import com.ciaranwood.swfjs.shape.CommandWithStartPoint;
import com.ciaranwood.vultan.tags.DefineMorphShape;
import com.ciaranwood.vultan.types.*;
import com.ciaranwood.vultan.types.Shape;

import java.util.ArrayList;
import java.util.List;

public class DefineMorphShapeProcessor {

    private final RectBoundsProcessor boundsProcessor = new RectBoundsProcessor();

    private FillStyleArray fillStyles;
    private LineStyleArray lineStyles;

    public Morph process(DefineMorphShape tag) {
        List<FillStyle> startFillStylesList = new ArrayList<FillStyle>();
        List<FillStyle> endFillStylesList = new ArrayList<FillStyle>();
        for(MorphFillStyle morphFillStyle : tag.morphFillStyles.fillStyles) {
            FillStyle startFillStyle = new FillStyle();
            startFillStyle.colorv3 = morphFillStyle.startColor;
            startFillStyle.fillStyleType = morphFillStyle.fillStyleType;
            startFillStyle.gradientMatrix = morphFillStyle.startGradientMatrix;
            
            startFillStylesList.add(startFillStyle);

            FillStyle endFillStyle = new FillStyle();
            endFillStyle.colorv3 = morphFillStyle.endColor;
            endFillStyle.fillStyleType = morphFillStyle.fillStyleType;
            endFillStyle.gradientMatrix = morphFillStyle.endGradientMatrix;

            endFillStylesList.add(endFillStyle);
        }
        
        List<LineStyle> startLineStylesList = new ArrayList<LineStyle>();
        List<LineStyle> endLineStylesList = new ArrayList<LineStyle>();
        for(MorphLineStyle morphLineStyle : tag.morphLineStyles.lineStyles) {
            LineStyle startLineStyle = new LineStyle();
            startLineStyle.colorv3 = morphLineStyle.startColor;
            startLineStyle.width = morphLineStyle.startWidth;
            startLineStylesList.add(startLineStyle);

            LineStyle endLineStyle = new LineStyle();
            endLineStyle.colorv3 = morphLineStyle.endColor;
            endLineStyle.width = morphLineStyle.endWidth;
            endLineStylesList.add(endLineStyle);
        }
        
        
        fillStyles = new FillStyleArray();
        fillStyles.fillStyles = startFillStylesList.toArray(new FillStyle[startFillStylesList.size()]);

        lineStyles = new LineStyleArray();
        lineStyles.lineStyles = startLineStylesList.toArray(new LineStyle[startLineStylesList.size()]);
        MorphCommands start = getCommands(tag.startEdges.shapeRecords, tag.startBounds);

        fillStyles = new FillStyleArray();
        fillStyles.fillStyles = endFillStylesList.toArray(new FillStyle[endFillStylesList.size()]);

        lineStyles = new LineStyleArray();
        lineStyles.lineStyles = endLineStylesList.toArray(new LineStyle[endLineStylesList.size()]);

        List<ShapeRecord> fixedEndRecords = new ArrayList<ShapeRecord>(tag.endEdges.shapeRecords);
        for(int i=0; i<tag.startEdges.shapeRecords.size(); i++) {
            ShapeRecord startRecord = tag.startEdges.shapeRecords.get(i);
            ShapeRecord endRecord = fixedEndRecords.get(i);

            if(startRecord instanceof Shape.StyleChangeRecord) {
                if(endRecord instanceof Shape.StyleChangeRecord) {
                    Shape.StyleChangeRecord styledStart = (Shape.StyleChangeRecord) startRecord;
                    Shape.StyleChangeRecord styledEnd = (Shape.StyleChangeRecord) endRecord;

                    styledEnd.fillStyle0 = styledStart.fillStyle0;
                    styledEnd.fillStyle1 = styledStart.fillStyle1;
                    styledEnd.lineStyle = styledStart.lineStyle;
                    styledEnd.stateFillStyle0 = styledStart.stateFillStyle0;
                    styledEnd.stateFillStyle1 = styledStart.stateFillStyle1;
                    styledEnd.stateLineStyle = styledStart.stateLineStyle;
                } else {
                    fixedEndRecords.add(i, startRecord);
                }
            }
        }

        MorphCommands end = getCommands(fixedEndRecords, tag.endBounds);

        if(start.getCommands().size() != end.getCommands().size()) {
            fixMorphCommands(start, end);
        }

        return new Morph(tag.getCharacterId(), start, end);
    }

    private void fixMorphCommands(MorphCommands startCommands, MorphCommands endCommands) {
        List<ShapeCommand> largest = (startCommands.getCommands().size() > endCommands.getCommands().size()) ?
                startCommands.getCommands() : endCommands.getCommands();

        for(int i=0; i<largest.size(); i++) {
            ShapeCommand start = startCommands.getCommands().get(i);
            ShapeCommand end = endCommands.getCommands().get(i);

            if(start != null && end !=null && start.getType() != end.getType()) {
                ShapeCommandType type = start.getType();
                if(shouldRemoveCommand(type)) {
                    startCommands.getCommands().remove(i);
                }

                if(type == ShapeCommandType.LINE) {
                    endCommands.getCommands().add(i, start);
                }

                type = end.getType();
                if(shouldRemoveCommand(type)) {
                    endCommands.getCommands().remove(i);
                }
            }
        }
    }

    private boolean shouldRemoveCommand(ShapeCommandType type) {
        return type == ShapeCommandType.MOVE;
    }

    private MorphCommands getCommands(List<ShapeRecord> shapeRecords, Rect bounds) {
        final MorphCommands morphCommands = new MorphCommands(boundsProcessor.process(bounds));

        final List<CommandWithStartPoint> commands = new ArrayList<CommandWithStartPoint>();
        ShapeRecordsProcessor recordsProcessor = new ShapeRecordsProcessor(fillStyles, lineStyles);
        DefineStylesCommand styles = recordsProcessor.process(shapeRecords, new ShapeRecordsProcessor.CommandHandler() {
            public void onShapeCommand(CommandWithStartPoint command) {
                commands.add(command);
            }
        });

        new ShapeProcessor().process(morphCommands, commands);

        for(CommandWithStartPoint command : commands) {
            morphCommands.addCommand(command.getCommand());
        }

        morphCommands.addStyles(styles);

        return morphCommands;
    }
}
