package com.ciaranwood.swfjs.processor;

import com.ciaranwood.swfjs.model.Bounds;
import com.ciaranwood.swfjs.model.Header;
import com.ciaranwood.vultan.tags.SWFHeader;

public class SwfHeaderProcessor {

    public Header process(SWFHeader header) {
        Bounds bounds = new RectBoundsProcessor().process(header.frameSize);
        return new Header(header.frameRate, bounds);
    }
}
