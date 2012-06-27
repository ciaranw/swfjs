package com.ciaranwood.swfjs;

import java.util.HashMap;
import java.util.Map;

public class Symbols {

    private final Map<String, Integer> mappings;

    public Symbols() {
        this.mappings = new HashMap<String, Integer>();
    }

    public void add(String name, Integer characterId) {
        mappings.put(name, characterId);
    }
}
