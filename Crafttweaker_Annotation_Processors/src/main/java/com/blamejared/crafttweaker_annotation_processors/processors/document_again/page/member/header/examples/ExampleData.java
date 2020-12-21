package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExampleData {
    
    private final Map<String, Example> examples;
    
    public ExampleData() {
        this.examples = new HashMap<>(1);
    }
    
    public ExampleData(Example example) {
        this();
        this.examples.put(example.getName(), example);
    }
    
    public ExampleData(Map<String, Example> examples) {
        this.examples = examples;
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
        return examples.get(name);
    }
    
    public Optional<Example> tryGetExampleFor(String name) {
        return Optional.ofNullable(getExampleFor(name));
    }
    
    public Map<String, Example> getExampleMap() {
        return examples;
    }
}
