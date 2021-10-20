package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element.ClassTypeConverter;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.KnownTypeRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConversionRule;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.function.Predicate;

public class NativeTypeConversionRule implements NameConversionRule {
    
    private final KnownTypeRegistry knownTypeRegistry;
    private final ClassTypeConverter classTypeConverter;
    
    public NativeTypeConversionRule(KnownTypeRegistry knownTypeRegistry, ClassTypeConverter classTypeConverter) {
        
        this.knownTypeRegistry = knownTypeRegistry;
        this.classTypeConverter = classTypeConverter;
    }
    
    @Nullable
    @Override
    public TypeMirror convertZenCodeName(String zenCodeName) {
        
        return knownTypeRegistry.getAllNativeTypes()
                .filter(nameMatches(zenCodeName))
                .map(this::getExpandedType)
                .findFirst()
                .orElse(null);
    }
    
    private TypeMirror getExpandedType(TypeElement typeElement) {
        
        final NativeTypeRegistration annotation = typeElement.getAnnotation(NativeTypeRegistration.class);
        return classTypeConverter.getTypeMirror(annotation, NativeTypeRegistration::value);
    }
    
    
    private Predicate<TypeElement> nameMatches(String zenCodeName) {
        
        return typeElement -> typeElement.getAnnotation(NativeTypeRegistration.class)
                .zenCodeName()
                .equals(zenCodeName);
    }
    
}
