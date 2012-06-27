package com.ciaranwood.swfjs;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;

public class CanvasImpl implements Canvas {

    private final Writer writer;

    public CanvasImpl(Writer writer) {
        this.writer = writer;
    }

    public void translate(BigDecimal x, BigDecimal y) {
        write("ctx.translate(%s, %s);", x, y);
    }

    public void transform(int scaleX, int rotateSkew1, int rotateSkew0, int scaleY, int translateX, int translateY) {

    }

    public void setFillStyle() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLineStyle() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void lineTo(BigDecimal x, BigDecimal y) {
        write("ctx.lineTo(%s, %s);", x, y);
    }

    public void curveTo(BigDecimal controlX, BigDecimal controlY, BigDecimal anchorX, BigDecimal anchorY) {
        write("ctx.quadraticCurveTo(%s, %s, %s, %s);", controlX, controlY, anchorX, anchorY);
    }

    public void moveTo(BigDecimal x, BigDecimal y) {
        write("ctx.moveTo(%s, %s);", x, y);
    }

    public void startPath() {
        write("ctx.beginPath();");
    }

    public void closePath() {
        write("ctx.closePath();");
    }

    public void stroke() {
        write("ctx.stroke();");
    }

    public void save() {
        write("ctx.save();");
    }

    public void restore() {
        write("ctx.restore();");
    }

    private void write(String format, Object... args) {
        try {
            String formatted = String.format(format, args);
            writer.write(formatted);
            writer.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
