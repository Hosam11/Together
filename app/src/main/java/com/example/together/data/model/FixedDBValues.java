package com.example.together.data.model;

import java.util.HashMap;
import java.util.Map;

public class FixedDBValues {

    private Map<Integer, String> interests;

    public FixedDBValues() {
        interests = new HashMap<>();
        interests.put(0, "IOS");
        interests.put(1, "php");
        interests.put(2, "java");
        interests.put(3, "other");
    }

    public Map<Integer, String> getInterests() {
        return interests;
    }
}

