package com.ciaranwood.swfjs;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class SwfJsImpl implements SwfJs {

    private final Writer writer;
    private final String fieldName;
    private StringBuilder buffer;
    private Integer frame;

    public SwfJsImpl(Writer writer, String fieldName) {
        this.writer = writer;
        this.fieldName = fieldName;
        this.buffer = new StringBuilder();
        this.frame = 0;
    }

    public void addToStage(Integer layer, Integer characterId, Transform matrix) {
        write("stage.addToStage(%s, %s, %s);", layer, characterId, transformJson(matrix));
    }

    public void modify(Integer layer, Transform matrix) {
        write("stage.modify(%s, %s);", layer, transformJson(matrix));
    }

    public void replace(Integer layer, Integer newCharacterId, Transform matrix) {
        write("stage.replace(%s, %s, %s);", layer, newCharacterId, transformJson(matrix));
    }

    public void remove(Integer layer) {
        write("stage.remove(%s);", layer);
    }

    public void nextFrame() {
        try {
            writer.write(String.format("%s.addAction(%s, function(stage) {\n", fieldName, frame));
            writer.write(buffer.toString());
            writer.write("\n});\n");
            frame++;
            buffer = new StringBuilder();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String truncate(BigDecimal value) {
        DecimalFormat df = new DecimalFormat("0.####");
        return df.format(value.stripTrailingZeros());
    }

    private String transformJson(Transform matrix) {
        if (matrix == null) {
            return "{}";
        }


        Transform normalised = matrix.normalise();
        return String.format("{a: %s, b: %s, c: %s, d: %s, e: %s, f: %s}",
                truncate(normalised.a),
                truncate(normalised.b),
                truncate(normalised.c),
                truncate(normalised.d),
                truncate(normalised.e),
                truncate(normalised.f));
    }

    private void write(String format, Object... args) {
        String formatted = String.format(format, args);
        buffer.append(formatted);
        buffer.append("\n");
    }
}
