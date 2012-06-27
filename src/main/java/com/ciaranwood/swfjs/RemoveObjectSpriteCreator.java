package com.ciaranwood.swfjs;

import com.ciaranwood.vultan.tags.RemoveObject2;

public class RemoveObjectSpriteCreator implements SpriteCreator {

    private final RemoveObject2 tag;

    public RemoveObjectSpriteCreator(RemoveObject2 tag) {
        this.tag = tag;
    }

    public void create(SwfJs swfJs) {
        swfJs.remove(tag.depth);
    }
}
