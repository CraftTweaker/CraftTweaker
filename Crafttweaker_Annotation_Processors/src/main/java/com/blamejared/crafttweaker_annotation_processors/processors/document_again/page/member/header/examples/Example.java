package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples;

import java.util.ArrayList;
import java.util.List;

public class Example {
    
    private final List<String> textValues;
    private final String name;
    
    public Example(String name) {
        this.name = name;
        this.textValues = new ArrayList<>();
    }
    
    public Example(String name, String text) {
        this.textValues = new ArrayList<>();
        this.textValues.add(text);
        this.name = name;
    }
    
    public static Example merge(Example left, Example right) {
        if(!left.name.equals(right.name)) {
            throw new IllegalArgumentException("Names do not match! " + left.name + " != " + right.name);
        }
        
        final Example result = new Example(left.name);
        result.textValues.addAll(left.textValues);
        result.textValues.addAll(right.textValues);
        
        return result;
    }
    
    public void addTextValue(String textValue) {
        this.textValues.add(textValue);
    }
    
    public int numberOfExamples() {
        return textValues.size();
    }
    
    public String getName() {
        return name;
    }
    
    public String getAnyTextValue() {
        return getTextValue(0);
    }
    
    public String getTextValue(int index) {
        return textValues.get(index);
    }
}
