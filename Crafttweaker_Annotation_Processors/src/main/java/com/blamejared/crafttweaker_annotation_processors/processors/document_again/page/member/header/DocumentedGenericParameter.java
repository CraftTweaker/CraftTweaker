package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DocumentedGenericParameter {
    
    
    private final String name;
    
    private final DocumentationComment comment;
    private final List<AbstractTypeInfo> bounds;
    
    public DocumentedGenericParameter(List<AbstractTypeInfo> bounds, String name, DocumentationComment comment) {
        this.bounds = bounds;
        this.name = name;
        this.comment = comment;
    }
    
    public String formatForSignatureExample() {
        if(bounds.isEmpty()) {
            return name;
        }
        
        final String prefix = name + " : ";
        return bounds.stream()
                .map(AbstractTypeInfo::getDisplayName)
                .collect(Collectors.joining(", ", prefix, ""));
    }
    
    public int numberOfExamples() {
        return comment.numberOfExamples();
    }
    
    public String getDescription() {
        return comment.getDescription();
    }
}
