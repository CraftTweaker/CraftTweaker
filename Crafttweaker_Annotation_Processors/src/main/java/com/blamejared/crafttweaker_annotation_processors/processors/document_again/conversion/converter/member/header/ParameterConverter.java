package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.*;

import javax.annotation.*;
import javax.lang.model.element.*;
import java.lang.annotation.*;
import java.util.*;
import java.util.function.*;

public class ParameterConverter {
    
    private final CommentConverter commentConverter;
    private final TypeConverter typeConverter;
    private final OptionalParameterConverter optionalParameterConverter;
    
    public ParameterConverter(CommentConverter commentConverter, TypeConverter typeConverter, OptionalParameterConverter optionalParameterConverter) {
        this.commentConverter = commentConverter;
        this.typeConverter = typeConverter;
        this.optionalParameterConverter = optionalParameterConverter;
    }
    
    public DocumentedParameter convertParameter(VariableElement variableElement) {
        if(isOptional(variableElement)) {
            return convertOptionalParameter(variableElement);
        } else {
            return convertNonOptionalParameter(variableElement);
        }
    }
    
    private boolean isOptional(VariableElement variableElement) {
        return optionalParameterConverter.isOptional(variableElement);
    }
    
    private String convertDefaultValue(VariableElement variableElement) {
        return optionalParameterConverter.convertDefaultValue(variableElement);
    }
    
    private DocumentedParameter convertOptionalParameter(VariableElement variableElement) {
        final String name = convertName(variableElement);
        final AbstractTypeInfo type = convertType(variableElement);
        final DocumentationComment comment = convertComment(variableElement);
        final String defaultValue = convertDefaultValue(variableElement);
        
        return new DocumentedOptionalParameter(name, type, comment, defaultValue);
    }
    
    private DocumentedParameter convertNonOptionalParameter(VariableElement variableElement) {
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
