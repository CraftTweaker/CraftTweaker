package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;

public class DocumentedParameter implements Comparable<DocumentedParameter> {
    
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
        return comment.getMarkdownDescription();
    }
    
    public boolean isOptional() {
        return false;
    }
    
    public void writeParameterInfoIncludeOptionality(PageOutputWriter writer) {
        writer.printf("| %s | %s | %s | %s | %s |%n", name, type.getClickableMarkdown(), getDescription(), false, "");
    }
    
    public void writeParameterInfoExcludeOptionality(PageOutputWriter writer) {
        writer.printf("| %s | %s | %s |%n", name, type.getClickableMarkdown(), getDescription());
    }
    
    public String getExample(int index) {
        return comment.getExamples().getExampleFor(name).getTextValue(index);
    }
    
    @Override
    public int compareTo(@Nonnull DocumentedParameter other) {
        int temp = this.name.compareTo(other.name);
        if(temp != 0) {
            return temp;
        }
        
        return this.type.compareTo(other.type);
    }
}
