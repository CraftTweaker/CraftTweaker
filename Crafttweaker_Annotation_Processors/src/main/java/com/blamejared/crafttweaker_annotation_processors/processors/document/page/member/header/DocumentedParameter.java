package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import java.io.PrintWriter;

public class DocumentedParameter {
    
    protected final String name;
    protected final AbstractTypeInfo type;
    protected final DocumentationComment comment;
    
    public DocumentedParameter(String name, AbstractTypeInfo type, DocumentationComment comment) {
        this.name = name;
        this.type = type;
        this.comment = comment;
    }
    
    public String formatForSignatureExample() {
        return String.format("%s as %s", name, type.getDisplayName());
    }
    
    public int numberOfExamples() {
        return comment.numberOfExamplesFor(name);
    }
    
    public String getDescription() {
        return comment.getDescription();
    }
    
    public boolean isOptional() {
        return false;
    }
    
    public void writeParameterInfoIncludeOptionality(PrintWriter writer) {
        writer.printf("| %s | %s | %s | %s | %s |%n", name, type.getClickableMarkdown(), getDescription(), false, "");
    }
    
    public void writeParameterInfoExcludeOptionality(PrintWriter writer) {
        writer.printf("| %s | %s | %s |%n", name, type.getClickableMarkdown(), getDescription());
    }
    
    public String getExample(int index) {
        return comment.getExamples().getExampleFor(name).getTextValue(index);
    }
}
