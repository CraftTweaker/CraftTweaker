package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nullable;
import java.io.PrintWriter;

public class DocumentedOptionalParameter extends DocumentedParameter {
    
    @Nullable
    private final String defaultValue;
    
    public DocumentedOptionalParameter(String name, AbstractTypeInfo type, DocumentationComment comment, @Nullable String defaultValue) {
        super(name, type, comment);
        this.defaultValue = defaultValue;
    }
    
    @Override
    public void writeParameterInfoIncludeOptionality(PrintWriter writer) {
        writer.printf("| %s | %s | %s | %s | %s |", name, type.getClickableMarkdown(), getDescription(), true, getDefaultValue());
    }
    
    public String getDefaultValue() {
        return defaultValue == null ? "Error: None given" : defaultValue;
    }
    
    @Override
    public boolean isOptional() {
        return true;
    }
}
