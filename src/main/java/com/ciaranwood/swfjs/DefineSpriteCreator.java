package com.ciaranwood.swfjs;

import com.ciaranwood.vultan.tags.DefineSprite;
import com.ciaranwood.vultan.tags.PlaceObject2;
import com.ciaranwood.vultan.tags.RemoveObject2;
import com.ciaranwood.vultan.tags.ShowFrame;

public class DefineSpriteCreator implements SpriteCreator {

    private final DefineSprite sprite;

    public DefineSpriteCreator(DefineSprite sprite) {
        this.sprite = sprite;
    }

    public void create(SwfJs swfJs) {
        for(Object tag : sprite.tags) {
            SpriteCreator creator = null;
            if(tag instanceof PlaceObject2) {
                PlaceObject2 place = (PlaceObject2) tag;
                creator = new PlaceObject2SpriteCreator(place);
            } else if(tag instanceof ShowFrame) {
                creator = new ShowFrameSpriteCreator();
            } else if(tag instanceof RemoveObject2) {
                RemoveObject2 remove = (RemoveObject2) tag;
                creator = new RemoveObjectSpriteCreator(remove);
            }

            if(creator != null) {
                creator.create(swfJs);
            }
        }
    }
}
