package com.ciaranwood.swfjs;

import java.util.*;

public class Dictionary implements Iterable<Dictionary.Entry> {

    private final Map<Integer, Object> dictionary;

    public Dictionary() {
        this.dictionary = new LinkedHashMap<Integer, Object>();
    }

    public void add(Integer characterId, Object definitionTag) {
        dictionary.put(characterId, definitionTag);
    }

    public Object get(Integer characterId) {
        return dictionary.get(characterId);
    }

    public Iterator<Entry> iterator() {
        List<Entry> entries = new ArrayList<Entry>();
        for(Map.Entry<Integer, Object> entry : dictionary.entrySet()) {
            entries.add(new Entry(entry.getKey(), entry.getValue()));
        }

        return entries.iterator();
    }

    public static class Entry {
        private final Integer characterId;
        private final Object tag;
        private final Class<?> tagClass;

        private Entry(Integer characterId, Object tag) {
            this.characterId = characterId;
            this.tag = tag;
            this.tagClass = tag.getClass();
        }

        public Integer getCharacterId() {
            return characterId;
        }

        public Object getTag() {
            return tag;
        }

        public Class<?> getTagClass() {
            return tagClass;
        }
    }

}
