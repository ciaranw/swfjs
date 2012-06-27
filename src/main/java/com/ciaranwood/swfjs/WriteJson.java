package com.ciaranwood.swfjs;

import com.ciaranwood.swfjs.json.SWFJsonProducer;
import com.ciaranwood.vultan.SWF;
import com.ciaranwood.vultan.SwfParser;
import com.ciaranwood.vultan.SwfReader;
import com.ciaranwood.vultan.tags.DefinitionTag;
import com.ciaranwood.vultan.tags.SymbolClass;
import com.ciaranwood.vultan.types.SymbolEntry;
import org.codehaus.preon.DecodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class WriteJson {

    private static final Logger log = LoggerFactory.getLogger(WriteJson.class);

    public void write(String fileName) throws IOException, DecodingException {
        log.debug("writing json for {}", fileName);
        File file = new File(fileName);
        ByteBuffer buffer = SwfReader.readFromFile(file);

        SwfParser parser = new SwfParser(buffer);

        SWF swf = parser.parse();

        Dictionary dictionary = new Dictionary();
        Symbols symbols = new Symbols();

        for(Object tag : swf) {
            if(tag instanceof DefinitionTag) {
                DefinitionTag definitionTag = (DefinitionTag) tag;
                dictionary.add(definitionTag.getCharacterId(), tag);
            }

            if(tag instanceof SymbolClass) {
                SymbolClass symbolClass = (SymbolClass) tag;
                for(SymbolEntry entry : symbolClass.symbols) {
                    symbols.add(entry.name, entry.characterId);
                }
            }
        }

        SWFJsonProducer jsonProducer = new SWFJsonProducer();
        jsonProducer.writeJson(swf.getHeader(), dictionary, file.getName().replace(".swf", ""));
    }
}
