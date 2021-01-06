package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.converter;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.ExpansionInfo;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

public class TypedExpansionConverter {
    
    private final Elements elementUtils;
    
    public TypedExpansionConverter(Elements elementUtils) {
        this.elementUtils = elementUtils;
    }
    
    public ExpansionInfo convertTypedExpansion(TypeElement expansionType) {
        final TypeMirror expandedType = getExpandedType(expansionType);
        return new ExpansionInfo(expandedType, expansionType);
    }
    
    private TypeMirror getExpandedType(TypeElement expansionType) {
        final TypedExpansion annotation = expansionType.getAnnotation(TypedExpansion.class);
        
        try {
            return getTypeMirrorFromClass(annotation.value());
        } catch(MirroredTypeException exception) {
            return exception.getTypeMirror();
        }
    }
    
    private TypeMirror getTypeMirrorFromClass(Class<?> value) {
        return elementUtils.getTypeElement(value.getCanonicalName()).asType();
    }
    
    
}
