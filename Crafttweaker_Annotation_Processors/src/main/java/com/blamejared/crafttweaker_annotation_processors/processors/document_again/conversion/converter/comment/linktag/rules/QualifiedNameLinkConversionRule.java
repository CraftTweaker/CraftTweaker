package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.LinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

public class QualifiedNameLinkConversionRule implements LinkConversionRule {
    
    private final Elements elementUtils;
    private final TypeConverter typeConverter;
    
    public QualifiedNameLinkConversionRule(Elements elementUtils, TypeConverter typeConverter) {
        this.elementUtils = elementUtils;
        this.typeConverter = typeConverter;
    }
    
    @Override
    public boolean canConvert(String link) {
        return isQualifiedName(link);
    }
    
    private boolean isQualifiedName(String link) {
        return link.contains(".");
    }
    
    @Override
    public String convertToClickableMarkdown(String link, Element element) {
        final AbstractTypeInfo typeInfo = getTypeInfo(link);
        return typeInfo.getClickableMarkdown();
    }
    
    private AbstractTypeInfo getTypeInfo(String link) {
        final TypeMirror typeMirror = getTypeMirror(link);
        return typeConverter.convertType(typeMirror);
    }
    
    private TypeMirror getTypeMirror(String link) {
        final TypeElement typeElement = getTypeElement(link);
        return typeElement.asType();
    }
    
    private TypeElement getTypeElement(String link) {
        final TypeElement typeElement = elementUtils.getTypeElement(link);
        if(typeElement == null) {
            throw new IllegalArgumentException("Could not find type " + link);
        }
        return typeElement;
    }
}
