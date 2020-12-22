package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.ExampleData;

import java.util.Optional;

public class DocumentationComment {
    
    private final String description;
    private final ExampleData exampleData;
    
    public DocumentationComment(String description, ExampleData exampleData) {
        this.description = description;
        this.exampleData = exampleData;
    }
    
    public static DocumentationComment empty() {
        return new DocumentationComment(null, ExampleData.empty());
    }
    
    public ExampleData getExamples() {
        return exampleData;
    }
    
    public String getDescription() {
        return getOptionalDescription().orElse("No Description Provided");
    }
    
    public boolean hasDescription() {
        return getOptionalDescription().isPresent();
    }
    
    public Optional<String> getOptionalDescription() {
        return Optional.ofNullable(description);
    }
    
    public int numberOfExamplesFor(String name) {
        final Optional<Example> example = exampleData.tryGetExampleFor(name);
        return example.map(Example::numberOfExamples).orElse(0);
    }
}
