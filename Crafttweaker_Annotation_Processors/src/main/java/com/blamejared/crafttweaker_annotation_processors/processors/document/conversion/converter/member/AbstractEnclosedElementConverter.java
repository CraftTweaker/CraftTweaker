package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.info.DocumentationPageInfo;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

public abstract class AbstractEnclosedElementConverter<T> {
    
    
    protected AbstractEnclosedElementConverter() {
    }
    
    protected boolean isAnnotationPresentOn(Class<? extends Annotation> annotationClass, Element element) {
        return element.getAnnotation(annotationClass) != null;
    }
    
    public abstract boolean canConvert(Element enclosedElement);
    
    public abstract void convertAndAddTo(Element enclosedElement, T result, DocumentationPageInfo pageInfo);
}
