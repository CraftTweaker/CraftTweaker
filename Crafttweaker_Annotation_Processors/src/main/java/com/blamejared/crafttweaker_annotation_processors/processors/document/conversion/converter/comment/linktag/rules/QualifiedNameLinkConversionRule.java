package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.linktag.LinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.util.Optional;

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
    public Optional<String> tryConvertToClickableMarkdown(String link, Element element) {
        
        final AbstractTypeInfo typeInfo = getTypeInfo(link);
        return Optional.ofNullable(typeInfo.getClickableMarkdown());
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
        
        String changingLink = link;
        TypeElement typeElement = elementUtils.getTypeElement(changingLink);
        
        while(typeElement == null && changingLink.lastIndexOf('.') > 0) {
            final int index = changingLink.lastIndexOf('.');
            
            final StringBuilder stringBuilder = new StringBuilder(changingLink);
            stringBuilder.setCharAt(index, '$');
            changingLink = stringBuilder.toString();
            
            typeElement = elementUtils.getTypeElement(changingLink);
        }
        
        if(typeElement == null) {
            throw new IllegalArgumentException("Could not find type " + link);
        }
        return typeElement;
    }
    
}
