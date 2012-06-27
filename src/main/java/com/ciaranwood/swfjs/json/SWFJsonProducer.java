package com.ciaranwood.swfjs.json;

import com.ciaranwood.swfjs.Dictionary;
import com.ciaranwood.swfjs.processor.DefineMorphShapeProcessor;
import com.ciaranwood.swfjs.processor.DefineShapeProcessor;
import com.ciaranwood.swfjs.processor.DefineSpriteProcessor;
import com.ciaranwood.swfjs.processor.SwfHeaderProcessor;
import com.ciaranwood.vultan.tags.DefineMorphShape;
import com.ciaranwood.vultan.tags.DefineShape;
import com.ciaranwood.vultan.tags.DefineSprite;
import com.ciaranwood.vultan.tags.SWFHeader;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SWFJsonProducer {

    public void writeJson(SWFHeader header, Dictionary dictionary, String name) throws IOException {
        File file = new File("target/" + name + ".json");
        List<Object> jsonObjects = new ArrayList<Object>();

        SwfHeaderProcessor headerProcessor = new SwfHeaderProcessor();
        DefineSpriteProcessor spriteProcessor = new DefineSpriteProcessor();

        jsonObjects.add(headerProcessor.process(header));

        for(Dictionary.Entry e : dictionary) {
            if(DefineShape.class.isAssignableFrom(e.getTagClass())) {
                DefineShape defineShape = (DefineShape) e.getTag();
                DefineShapeProcessor shapeProcessor = new DefineShapeProcessor();
                jsonObjects.add(shapeProcessor.process(defineShape));
            }

            if(DefineMorphShape.class.isAssignableFrom(e.getTagClass())) {
                DefineMorphShape morphShape = (DefineMorphShape) e.getTag();
                DefineMorphShapeProcessor morphShapeProcessor = new DefineMorphShapeProcessor();
                jsonObjects.add(morphShapeProcessor.process(morphShape));
            }

            if(e.getTagClass().equals(DefineSprite.class)) {
                DefineSprite defineSprite = (DefineSprite) e.getTag();
                jsonObjects.add(spriteProcessor.process(defineSprite));
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.writeValue(file, jsonObjects);
    }
}
