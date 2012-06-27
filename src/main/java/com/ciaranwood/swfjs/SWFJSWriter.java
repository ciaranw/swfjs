package com.ciaranwood.swfjs;

import com.ciaranwood.swfjs.model.Point;
import com.ciaranwood.vultan.tags.DefineShape;
import com.ciaranwood.vultan.tags.DefineSprite;
import com.ciaranwood.vultan.tags.SWFHeader;

import java.io.*;
import java.math.BigDecimal;

public class SWFJSWriter {

    private final Dictionary dictionary;
    private final Symbols symbols;
    private final SWFHeader header;

    public SWFJSWriter(Dictionary dictionary, Symbols symbols, SWFHeader header) {
        this.dictionary = dictionary;
        this.symbols = symbols;
        this.header = header;
    }

    public void write(String name) throws IOException {
        Writer writer = new FileWriter(new File("target/" + name + ".js"));

        writer.write("(function() {\n");

        for(Dictionary.Entry e : dictionary) {
            if(DefineShape.class.isAssignableFrom(e.getTagClass())) {
                String shapeName = String.format("s%s", e.getCharacterId());
                writer.write(String.format("  var %s = new swfjs.Shape(%s);\n", shapeName, e.getCharacterId()));

                DefineShapeRenderer renderer = new DefineShapeRenderer((DefineShape) e.getTag());
                StringWriter canvasWriter = new StringWriter();
                Canvas canvas = new CanvasImpl(canvasWriter);
                renderer.render(canvas, new Point(BigDecimal.ZERO, BigDecimal.ZERO));

                writer.write(String.format("  %s.draw = function(ctx) {\n", shapeName));
                writer.write("    " + canvasWriter.toString());
                writer.write("  };\n");

                writer.write(String.format("  swfJs.defineShape(%s);\n", shapeName));

            }

            if(e.getTagClass().equals(DefineSprite.class)) {
                String spriteName = String.format("sp%s", e.getCharacterId());
                DefineSprite sprite = (DefineSprite) e.getTag();
                writer.write(String.format("var %s = new swfjs.Sprite(%s, %s, %s);\n",
                        spriteName, e.getCharacterId(), sprite.frameCount, header.frameRate));

                writer.write(String.format("%s.create = function() {\n", spriteName));
                StringWriter swfJsWriter = new StringWriter();
                SwfJs swfJs = new SwfJsImpl(swfJsWriter, spriteName);
                new DefineSpriteCreator(sprite).create(swfJs);
                writer.write(swfJsWriter.toString());
                writer.write("};\n");

                writer.write(String.format("swfJs.defineShape(%s);\n", spriteName));

            }
        }


        writer.write("})();");
        writer.flush();
    }
}
