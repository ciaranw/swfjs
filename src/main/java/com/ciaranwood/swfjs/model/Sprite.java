package com.ciaranwood.swfjs.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Sprite {

    private final Integer characterId;
    private final Integer frameCount;
    private final Map<Integer, List<FrameAction>> frames;

    public Sprite(Integer characterId, Integer frameCount) {
        this.characterId = characterId;
        this.frameCount = frameCount;
        this.frames = new LinkedHashMap<Integer, List<FrameAction>>();
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public Integer getFrameCount() {
        return frameCount;
    }

    public Map<Integer, List<FrameAction>> getFrames() {
        return frames;
    }

    public void addFrame(Integer index, List<FrameAction> frame) {
        frames.put(index, frame);
    }

    public TagType getType() {
        return TagType.SPRITE;
    }
}
