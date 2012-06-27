package com.ciaranwood.swfjs;

import com.ciaranwood.vultan.tags.PlaceObject2;

public class PlaceObject2SpriteCreator implements SpriteCreator {

    private final PlaceObject2 tag;

    public PlaceObject2SpriteCreator(PlaceObject2 tag) {
        this.tag = tag;
    }

    public void create(SwfJs swfJs) {
        Transform matrix = null;
        if(tag.placeFlagHasMatrix) {
            matrix = new Transform();
            matrix.a = tag.matrix.scaleX;
            matrix.b = tag.matrix.rotateSkew0;
            matrix.c = tag.matrix.rotateSkew1;
            matrix.d = tag.matrix.scaleY;
            matrix.e = tag.matrix.translateX.getPixels();
            matrix.f = tag.matrix.translateY.getPixels();
        }

        if(tag.isAddNewCharacter()) {
            swfJs.addToStage(tag.depth, tag.characterId, matrix);
        } else if(tag.isModifyExistingCharacter()) {
            swfJs.modify(tag.depth, matrix);
        } else if(tag.isReplaceCharacter()) {
            swfJs.replace(tag.depth, tag.characterId, matrix);
        }

    }
}
