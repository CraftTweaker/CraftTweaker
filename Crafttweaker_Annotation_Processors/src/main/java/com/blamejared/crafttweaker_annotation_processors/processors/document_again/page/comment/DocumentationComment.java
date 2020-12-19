package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.ExampleData;

import java.util.Optional;

public class DocumentationComment {
    
    private final String description;
    private final ExampleData exampleData;
    
    public DocumentationComment(String description, ExampleData exampleData) {
        this.description = description;
        this.exampleData = exampleData;
    }
    
    public ExampleData getExamples() {
        return exampleData;
    }
    
    public int numberOfExamples() {
        return exampleData.numberOfExamples();
    }
    
    public String getDescription() {
        return getOptionalDescription().orElse("No Description Provided");
    }
    
    public Optional<String> getOptionalDescription() {
        return Optional.ofNullable(description);
    }
}
