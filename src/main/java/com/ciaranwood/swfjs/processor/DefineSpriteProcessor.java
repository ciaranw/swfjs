package com.ciaranwood.swfjs.processor;

import com.ciaranwood.swfjs.Transform;
import com.ciaranwood.swfjs.model.*;
import com.ciaranwood.vultan.tags.DefineSprite;
import com.ciaranwood.vultan.tags.PlaceObject2;
import com.ciaranwood.vultan.tags.RemoveObject2;
import com.ciaranwood.vultan.tags.ShowFrame;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DefineSpriteProcessor {

    public Sprite process(DefineSprite defineSprite) {

        Sprite sprite = new Sprite(defineSprite.getCharacterId(), defineSprite.frameCount);

        Integer frame = 0;
        List<FrameAction> actions = new ArrayList<FrameAction>();
        for(Object tag : defineSprite.tags) {
            if(tag instanceof PlaceObject2) {
                PlaceObject2 place = (PlaceObject2) tag;
                actions.addAll(handle(place));
            } else if(tag instanceof ShowFrame) {
                sprite.addFrame(frame, new ArrayList<FrameAction>(actions));
                actions.clear();
                frame++;
            } else if(tag instanceof RemoveObject2) {
                RemoveObject2 remove = (RemoveObject2) tag;
                actions.add(new RemoveFrameAction(remove.depth));
            }
        }

        return sprite;
    }

    private List<FrameAction> handle(PlaceObject2 tag) {
        List<FrameAction> actions = new ArrayList<FrameAction>();
        BigDecimal[] matrixParams = null;
        if(tag.placeFlagHasMatrix) {
            Transform matrix = new Transform();
            matrix.a = tag.matrix.scaleX;
            matrix.b = tag.matrix.rotateSkew0;
            matrix.c = tag.matrix.rotateSkew1;
            matrix.d = tag.matrix.scaleY;
            matrix.e = tag.matrix.translateX.getPixels();
            matrix.f = tag.matrix.translateY.getPixels();
            matrixParams = createMatrixArgs(matrix);
        }

        if(tag.isAddNewCharacter()) {
            actions.add(new AddFrameAction(tag.depth, tag.characterId, matrixParams, tag.placeFlagHasClipDepth));
        } else if(tag.isModifyExistingCharacter()) {
            actions.add(new ModifyFrameAction(tag.depth, matrixParams));
        } else if(tag.isReplaceCharacter()) {
            actions.add(new ReplaceFrameAction(tag.depth, tag.characterId, matrixParams));
        } else {
            throw new RuntimeException("Can't handle PlaceObject tag");
        }

        if(tag.placeFlagHasRatio) {
            actions.add(new RatioFrameAction(tag.depth, tag.ratio));
        }

        return actions;
    }

    private BigDecimal truncate(BigDecimal value) {
        DecimalFormat df = new DecimalFormat("0.####");
        String formatted = df.format(value.stripTrailingZeros());
        return new BigDecimal(formatted);
    }

    private BigDecimal[] createMatrixArgs(Transform matrix) {
        matrix = matrix.normalise();

        BigDecimal[] params = new BigDecimal[6];
        params[0] = truncate(matrix.a);
        params[1] = truncate(matrix.b);
        params[2] = truncate(matrix.c);
        params[3] = truncate(matrix.d);
        params[4] = truncate(matrix.e);
        params[5] = truncate(matrix.f);

        return params;
    }

}
