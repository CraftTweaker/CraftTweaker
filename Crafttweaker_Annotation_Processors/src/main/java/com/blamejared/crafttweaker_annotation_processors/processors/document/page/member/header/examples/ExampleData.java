package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExampleData {
    
    private final Map<String, Example> examples;
    
    private ExampleData() {
        
        this.examples = new HashMap<>(1);
    }
    
    public ExampleData(Example example) {
        
        this();
        this.examples.put(example.getName(), example);
    }
    
    public ExampleData(Map<String, Example> examples) {
        
        this.examples = examples;
    }
    
    public static ExampleData empty() {
        
        return new ExampleData();
    }
    
    public void addExample(Example example) {
        
        this.examples.put(example.getName(), example);
    }
    
    public int numberOfExamples() {
        
        return examples.values().stream().mapToInt(Example::numberOfExamples).min().orElse(0);
    }
    
    public boolean hasExampleFor(String name) {
        
        return examples.containsKey(name);
    }
    
    public Example getExampleFor(String name) {
        
        return tryGetExampleFor(name).orElseThrow(() -> new IllegalArgumentException("No example registered for: " + name));
    }
    
    public Optional<Example> tryGetExampleFor(String name) {
        
        if(hasExampleFor(name)) {
            return Optional.of(examples.get(name));
        }
        return Optional.empty();
    }
    
    public Map<String, Example> getExampleMap() {
        
        return examples;
    }
    
}
