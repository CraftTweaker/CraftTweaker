package com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment;

import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.MetaData;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.ExampleData;

import java.util.Optional;

public class DocumentationComment {
    
    private final String description;
    private final String deprecationMessage;
    private final String sinceVersion;
    private final ExampleData exampleData;
    private final MetaData metaData;
    
    public DocumentationComment(final String description, final String deprecationMessage, final String sinceVersion,
                                final ExampleData data, final MetaData metaData) {
        this.description = description;
        this.deprecationMessage = deprecationMessage == null? null : deprecationMessage.replaceAll("(\r)?\n", "");
        this.sinceVersion = sinceVersion;
        this.exampleData = data;
        this.metaData = metaData;
    }
    
    public static DocumentationComment empty() {
        return new DocumentationComment(null, null, null, ExampleData.empty(), MetaData.empty());
    }
    
    public ExampleData getExamples() {
        return exampleData;
    }
    
    public MetaData getMetaData() {
        
        return metaData;
    }
    
    public String getDescription() {
        return getOptionalDescription().orElse("No Description Provided");
    }
    
    public String getMarkdownDescription(){
        return getDescription().replaceAll("\n", " <br /> ");
    }
    
    public boolean hasDescription() {
        return getOptionalDescription().isPresent();
    }
    
    public Optional<String> getOptionalDescription() {
        return Optional.ofNullable(description);
    }
    
    public String getDeprecationMessage() {
        return this.deprecationMessage;
    }
    
    public boolean isDeprecated() {
        return this.deprecationMessage != null;
    }
    
    public Optional<String> getOptionalDeprecationMessage() {
        return Optional.ofNullable(this.deprecationMessage);
    }
    
    public String getSinceVersion() {
        return this.sinceVersion;
    }
    
    public Optional<String> getOptionalSince() {
        return Optional.ofNullable(this.sinceVersion);
    }
    
    public int numberOfExamplesFor(String name) {
        final Optional<Example> example = exampleData.tryGetExampleFor(name);
        return example.map(Example::numberOfExamples).orElse(0);
    }
}
