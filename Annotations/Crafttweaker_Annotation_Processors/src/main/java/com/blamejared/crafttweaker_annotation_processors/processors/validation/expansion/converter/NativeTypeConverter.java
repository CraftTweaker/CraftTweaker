package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.converter;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element.ClassTypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.ExpansionInfo;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class NativeTypeConverter {
    
    private final ClassTypeConverter classTypeConverter;
    
    public NativeTypeConverter(ClassTypeConverter classTypeConverter) {
        
        this.classTypeConverter = classTypeConverter;
    }
    
    
    public ExpansionInfo convertNativeType(TypeElement element) {
        
        final TypeMirror expandedType = getExpandedType(element);
        return new ExpansionInfo(expandedType, element);
    }
    
    private TypeMirror getExpandedType(TypeElement typeMirror) {
        
        final NativeTypeRegistration annotation = typeMirror.getAnnotation(NativeTypeRegistration.class);
        return classTypeConverter.getTypeMirror(annotation, NativeTypeRegistration::value);
    }
    
}
