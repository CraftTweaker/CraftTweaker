package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.DocumentedParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.lang.model.element.VariableElement;

public class ParameterConverter {
    
    private final CommentConverter commentConverter;
    private final TypeConverter typeConverter;
    
    public ParameterConverter(CommentConverter commentConverter, TypeConverter typeConverter) {
        this.commentConverter = commentConverter;
        this.typeConverter = typeConverter;
    }
    
    public DocumentedParameter convertParameter(VariableElement variableElement) {
        final String name = convertName(variableElement);
        final AbstractTypeInfo type = convertType(variableElement);
        final DocumentationComment comment = convertComment(variableElement);
        
        return new DocumentedParameter(name, type, comment);
    }
    
    private String convertName(VariableElement variableElement) {
        return variableElement.getSimpleName().toString();
    }
    
    private AbstractTypeInfo convertType(VariableElement variableElement) {
        return typeConverter.convertType(variableElement.asType());
    }
    
    private DocumentationComment convertComment(VariableElement variableElement) {
        return commentConverter.convertForParameter(variableElement);
    }
}
