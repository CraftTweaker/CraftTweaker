package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.converter;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.ExpansionInfo;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

public class NativeTypeConverter {
    
    private final Elements elementUtils;
    
    public NativeTypeConverter(Elements elementUtils) {
        this.elementUtils = elementUtils;
    }
    
    public ExpansionInfo convertNativeType(TypeElement element) {
        final TypeMirror expandedType = getExpandedType(element);
        return new ExpansionInfo(expandedType, element);
    }
    
    private TypeMirror getExpandedType(TypeElement typeMirror) {
        final NativeTypeRegistration annotation = typeMirror.getAnnotation(NativeTypeRegistration.class);
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
