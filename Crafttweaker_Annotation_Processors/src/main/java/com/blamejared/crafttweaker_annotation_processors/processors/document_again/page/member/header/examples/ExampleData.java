package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples;

import java.util.HashMap;
import java.util.Map;

public class ExampleData {
    
    private final Map<String, Example> examples = new HashMap<>(1);
    
    public ExampleData(Example example) {
        this.examples.put(example.getName(), example);
    }
    
    public ExampleData() {
    
    }
    
    public void addExample(Example example) {
        this.examples.put(example.getName(), example);
    }
    
    public int numberOfExamples() {
        return examples.size();
    }
    
    public boolean hasExampleFor(String name) {
        return examples.containsKey(name);
    }
    
    public Example getExampleFor(String name) {
        return examples.get(name);
    }
}
