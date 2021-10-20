package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.converter;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element.ClassTypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.ExpansionInfo;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class TypedExpansionConverter {
    
    private final ClassTypeConverter classTypeConverter;
    
    public TypedExpansionConverter(ClassTypeConverter classTypeConverter) {
        
        this.classTypeConverter = classTypeConverter;
    }
    
    public ExpansionInfo convertTypedExpansion(TypeElement expansionType) {
        
        final TypeMirror expandedType = getExpandedType(expansionType);
        return new ExpansionInfo(expandedType, expansionType);
    }
    
    private TypeMirror getExpandedType(TypeElement expansionType) {
        
        final TypedExpansion annotation = expansionType.getAnnotation(TypedExpansion.class);
        return classTypeConverter.getTypeMirror(annotation, TypedExpansion::value);
    }
    
}
