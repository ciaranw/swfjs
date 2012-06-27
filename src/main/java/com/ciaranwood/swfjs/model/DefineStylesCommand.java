package com.ciaranwood.swfjs.model;

import java.util.List;

public class DefineStylesCommand implements ShapeCommand {

    private final List<ShapeFillStyle> fillStyles;
    private final List<ShapeLineStyle> lineStyles;

    public DefineStylesCommand(List<ShapeFillStyle> fillStyles, List<ShapeLineStyle> lineStyles) {
        this.fillStyles = fillStyles;
        this.lineStyles = lineStyles;
    }

    public ShapeCommandType getType() {
        return ShapeCommandType.STYLES;
    }

    public List<ShapeFillStyle> getFillStyles() {
        return fillStyles;
    }

    public List<ShapeLineStyle> getLineStyles() {
        return lineStyles;
    }
}
