package com.ciaranwood.swfjs.processor;

import com.ciaranwood.swfjs.model.*;
import com.ciaranwood.swfjs.shape.CommandWithStartPoint;
import com.ciaranwood.vultan.types.*;
import com.ciaranwood.vultan.types.Shape;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShapeRecordsProcessor {

    private final FillStyleArray initialFillStyles;
    private final LineStyleArray initialLineStyles;

    private Integer fillStyle0;
    private Integer fillStyle1;
    private Integer lineStyle;

    private List<FillStyle> fillStyles;
    private List<LineStyle> lineStyles;
    
    private Map<Integer, Integer> currentFillStylesMapping;
    private Map<Integer, Integer> currentLineStylesMapping;

    public ShapeRecordsProcessor(FillStyleArray initialFillStyles, LineStyleArray initialLineStyles) {
        this.initialFillStyles = initialFillStyles;
        this.initialLineStyles = initialLineStyles;
        
        this.fillStyles = new ArrayList<FillStyle>();
        this.lineStyles = new ArrayList<LineStyle>();
        
        this.currentFillStylesMapping = new HashMap<Integer, Integer>();
        this.currentLineStylesMapping = new HashMap<Integer, Integer>();
    }

    public DefineStylesCommand process(List<ShapeRecord> records, CommandHandler handler) {
        Point position = new Point(BigDecimal.ZERO, BigDecimal.ZERO);

        addStyles(initialFillStyles, initialLineStyles);

        for(ShapeRecord record : records) {
            if(record instanceof com.ciaranwood.vultan.types.Shape.StyleChangeRecord) {
                Shape.StyleChangeRecord styleChange = (Shape.StyleChangeRecord) record;
                position = handle(styleChange, position, handler);
            } else if(record instanceof StraightEdgeRecord) {
                StraightEdgeRecord straightEdge = (StraightEdgeRecord) record;
                position = handle(straightEdge, position, handler);
            } else if(record instanceof CurvedEdgeRecord) {
                CurvedEdgeRecord curvedEdge = (CurvedEdgeRecord) record;
                position = handle(curvedEdge, position, handler);
            }
        }
        
        return buildReplaceStyles(fillStyles, lineStyles);
    }

    private void addStyles(FillStyleArray fillStyles, LineStyleArray lineStyles) {
        for(int i=0; i<fillStyles.fillStyles.length; i++) {
            FillStyle fillStyle = fillStyles.fillStyles[i];
            if(!this.fillStyles.contains(fillStyle)) {
                this.fillStyles.add(fillStyle);    
            }
            
            currentFillStylesMapping.put(i + 1, this.fillStyles.indexOf(fillStyle));
        }

        for(int i=0; i<lineStyles.lineStyles.length; i++) {
            LineStyle lineStyle = lineStyles.lineStyles[i];
            if(!this.lineStyles.contains(lineStyle)) {
                this.lineStyles.add(lineStyle);
            }

            currentLineStylesMapping.put(i + 1, this.lineStyles.indexOf(lineStyle));
        }
    }

    private Point handle(Shape.StyleChangeRecord record, Point position, CommandHandler handler) {
        Point newPosition = position;
        if(record.stateMoveTo) {
            Point moveTo = new Point(record.moveDeltaX.getPixels(), record.moveDeltaY.getPixels());
            ShapeCommand command = new MoveShapeCommand(moveTo);
            handler.onShapeCommand(new CommandWithStartPoint(command, position));
            newPosition = moveTo;
        }

        if(record.stateNewStyles) {
            addStyles(record.fillStyles, record.lineStyles);
            fillStyle0 = null;
            fillStyle1 = null;
            lineStyle = null;
        }

        if(record.stateFillStyle0) {
            fillStyle0 = currentFillStylesMapping.get(record.fillStyle0);
        }

        if(record.stateFillStyle1) {
            fillStyle1 = currentFillStylesMapping.get(record.fillStyle1);
        }

        if(record.stateLineStyle) {
            lineStyle = currentLineStylesMapping.get(record.lineStyle);
        }

        return newPosition;
    }

    private DefineStylesCommand buildReplaceStyles(List<FillStyle> fillStyles, List<LineStyle> lineStyles) {
        List<ShapeFillStyle> shapeFillStyles = new ArrayList<ShapeFillStyle>();
        for(FillStyle fillStyle : fillStyles) {
            shapeFillStyles.add(new ShapeFillStyle(getColor(fillStyle.getColor())));
        }

        List<ShapeLineStyle> shapeLineStyles = new ArrayList<ShapeLineStyle>();
        for(LineStyle lineStyle : lineStyles) {
            shapeLineStyles.add(new ShapeLineStyle(lineStyle.width.getPixels(), getColor(lineStyle.getColor())));
        }

        return new DefineStylesCommand(shapeFillStyles, shapeLineStyles);
    }

    private String getColor(RGB rgb) {
        Float alpha = 255.0f;
        if(rgb instanceof RGBA) {
            alpha = ((RGBA) rgb).alpha.floatValue();
        }

        return String.format("rgba(%s, %s, %s, %s)", rgb.red, rgb.green, rgb.blue, alpha / 255.0f);
    }

    private Point handle(StraightEdgeRecord record, Point position, CommandHandler handler) {
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

        Point lineTo = position.add(deltaX, deltaY);
        ShapeCommand command = new StraightEdgeShapeCommand(lineTo, fillStyle0, fillStyle1, lineStyle);
        handler.onShapeCommand(new CommandWithStartPoint(command, position));
        return lineTo;
    }

    private Point handle(CurvedEdgeRecord record, Point position, CommandHandler handler) {
        Point control = position.add(record.controlDeltaX.getPixels(), record.controlDeltaY.getPixels());
        Point anchor  = control.add(record.anchorDeltaX.getPixels(), record.anchorDeltaY.getPixels());

        ShapeCommand command = new CurvedEdgeShapeCommand(control, anchor, fillStyle0, fillStyle1, lineStyle);
        handler.onShapeCommand(new CommandWithStartPoint(command, position));

        return anchor;
    }

    public interface CommandHandler {
        void onShapeCommand(CommandWithStartPoint command);
    }

}
