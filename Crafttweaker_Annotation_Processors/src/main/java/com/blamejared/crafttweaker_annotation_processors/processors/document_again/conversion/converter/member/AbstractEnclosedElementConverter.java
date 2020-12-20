package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

public abstract class AbstractEnclosedElementConverter<T> {
    
    protected final TypeConverter typeConverter;
    
    protected AbstractEnclosedElementConverter(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }
    
    protected boolean isAnnotationPresentOn(Class<? extends Annotation> annotationClass, Element element) {
        return element.getAnnotation(annotationClass) != null;
    }
    
    public abstract boolean canConvert(Element enclosedElement);
    
    public abstract void convertAndAddTo(Element enclosedElement, T result, DocumentationPageInfo pageInfo);
}
