package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;

import javax.lang.model.element.Element;

public abstract class AbstractEnclosedElementConverter<T> {
    
    protected final TypeConverter typeConverter;
    
    protected AbstractEnclosedElementConverter(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }
    
    public abstract boolean canConvert(Element enclosedElement);
    
    public abstract void convertAndAddTo(Element enclosedElement, T result, DocumentationPageInfo pageInfo);
}
