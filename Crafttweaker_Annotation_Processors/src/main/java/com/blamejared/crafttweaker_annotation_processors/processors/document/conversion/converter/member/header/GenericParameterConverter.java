package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.DocumentedGenericParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.lang.model.element.TypeParameterElement;
import java.util.List;
import java.util.stream.Collectors;

public class GenericParameterConverter {
    
    private final TypeConverter typeConverter;
    private final CommentConverter commentConverter;
    
    public GenericParameterConverter(TypeConverter typeConverter, CommentConverter commentConverter) {
        this.typeConverter = typeConverter;
        this.commentConverter = commentConverter;
    }
    
    public DocumentedGenericParameter convertGenericParameter(TypeParameterElement typeParameterElement) {
        final String name = convertName(typeParameterElement);
        final List<AbstractTypeInfo> bounds = convertBounds(typeParameterElement);
        final DocumentationComment comment = convertComment(typeParameterElement);
    
        return new DocumentedGenericParameter(bounds, name, comment);
    }
    
    private String convertName(TypeParameterElement typeParameterElement) {
        return typeParameterElement.getSimpleName().toString();
    }
    
    private DocumentationComment convertComment(TypeParameterElement typeParameterElement) {
        return commentConverter.convertForTypeParameter(typeParameterElement);
    }
    
    private List<AbstractTypeInfo> convertBounds(TypeParameterElement typeParameterElement) {
        return typeParameterElement.getBounds().stream()
                .map(typeConverter::convertType)
                .collect(Collectors.toList());
    }
}
