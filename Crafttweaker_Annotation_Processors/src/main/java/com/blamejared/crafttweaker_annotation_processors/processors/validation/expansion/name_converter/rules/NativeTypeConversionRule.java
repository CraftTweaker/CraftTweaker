package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.KnownTypeRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConversionRule;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.util.function.Predicate;

public class NativeTypeConversionRule implements NameConversionRule {
    
    private final KnownTypeRegistry knownTypeRegistry;
    private final Elements elementUtils;
    
    public NativeTypeConversionRule(KnownTypeRegistry knownTypeRegistry, Elements elementUtils) {
        this.knownTypeRegistry = knownTypeRegistry;
        this.elementUtils = elementUtils;
    }
    
    @Nullable
    @Override
    public TypeMirror convertZenCodeName(String zenCodeName) {
        return knownTypeRegistry.getNativeTypes()
                .stream()
                .filter(nameMatches(zenCodeName))
                .map(this::getExpandedType)
                .findFirst()
                .orElse(null);
    }
    
    private TypeMirror getExpandedType(TypeElement typeElement) {
        final NativeTypeRegistration annotation = typeElement.getAnnotation(NativeTypeRegistration.class);
        try {
            return getTypeMirrorFromClass(annotation.value());
        } catch(MirroredTypeException exception) {
            return exception.getTypeMirror();
        }
    }
    
    private TypeMirror getTypeMirrorFromClass(Class<?> value) {
        return elementUtils.getTypeElement(value.getCanonicalName()).asType();
    }
    
    private Predicate<TypeElement> nameMatches(String zenCodeName) {
        return typeElement -> typeElement.getAnnotation(NativeTypeRegistration.class)
                .zenCodeName()
                .equals(zenCodeName);
    }
}
