package com.example.together.data.model;

import java.util.HashMap;
import java.util.Map;

public class FixedDBValues {

//    private Map<Integer, String> interests;
    private Map<Integer, String> countries;
    private Map<Integer, String> levels;

    public FixedDBValues() {
//        interests = new HashMap<>();
        countries = new HashMap<>();
        levels = new HashMap<>();
     /*   interests.put(0, "IOS");
        interests.put(1, "php");
        interests.put(2, "java");
        interests.put(3, "other");*/

        countries.put(1, "Egypt");
        countries.put(2, "German");
        countries.put(3, "Spain");

        levels.put(1, "Beginner");
    }

//    public Map<Integer, String> getInterests() {
//        return interests;
//    }

    public Map<Integer, String> getCountries() {
        return countries;
    }

}

