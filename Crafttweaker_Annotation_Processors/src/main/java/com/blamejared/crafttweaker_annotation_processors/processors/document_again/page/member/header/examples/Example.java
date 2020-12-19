package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples;

import java.util.ArrayList;
import java.util.List;

public class Example {
    
    private final List<String> textValues;
    private final String name;
    
    
    public Example(String name, String text) {
        this.textValues = new ArrayList<>(1);
        this.textValues.add(text);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAnyTextValue() {
        return textValues.get(0);
    }
}
