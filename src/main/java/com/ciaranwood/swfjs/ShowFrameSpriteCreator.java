package com.ciaranwood.swfjs;

public class ShowFrameSpriteCreator implements SpriteCreator {

    public void create(SwfJs swfJs) {
        swfJs.nextFrame();
    }
}
