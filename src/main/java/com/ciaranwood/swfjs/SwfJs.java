package com.ciaranwood.swfjs;

public interface SwfJs {

    void addToStage(Integer layer, Integer characterId, Transform matrix);

    void modify(Integer layer, Transform matrix);

    void replace(Integer layer, Integer newCharacterId, Transform matrix);

    void remove(Integer layer);

    void nextFrame();
}
