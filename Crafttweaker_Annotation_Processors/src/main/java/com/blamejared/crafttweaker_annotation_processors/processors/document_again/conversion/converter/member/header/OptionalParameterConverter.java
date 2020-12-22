package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.member.header;

import org.openzen.zencode.java.*;

import javax.lang.model.element.*;
import java.lang.annotation.*;
import java.util.*;
import java.util.function.*;

public class OptionalParameterConverter {
    
    private final Map<Class<? extends Annotation>, Function<? extends Annotation, Object>> optionalAnnotations;
    
    public OptionalParameterConverter() {
        optionalAnnotations = new HashMap<>();
        addOptionalAnnotations();
    }
    
    private void addOptionalAnnotations() {
        addOptionalAnnotation(ZenCodeType.Optional.class, ZenCodeType.Optional::value);
        addOptionalAnnotation(ZenCodeType.OptionalInt.class, ZenCodeType.OptionalInt::value);
        addOptionalAnnotation(ZenCodeType.OptionalDouble.class, ZenCodeType.OptionalDouble::value);
        addOptionalAnnotation(ZenCodeType.OptionalLong.class, ZenCodeType.OptionalLong::value);
        addOptionalAnnotation(ZenCodeType.OptionalFloat.class, ZenCodeType.OptionalFloat::value);
        addOptionalAnnotation(ZenCodeType.OptionalString.class, ZenCodeType.OptionalString::value);
    }
    
    private <T extends Annotation> void addOptionalAnnotation(Class<T> annotationClass, Function<T, Object> converter) {
        optionalAnnotations.put(annotationClass, converter);
    }
    
    public boolean isOptional(VariableElement variableElement) {
        return optionalAnnotations.keySet().stream().anyMatch(hasAnnotation(variableElement));
    }
    
    private Predicate<? super Class<? extends Annotation>> hasAnnotation(VariableElement variableElement) {
        return annotationClass -> isAnnotationPresent(variableElement, annotationClass);
    }
    
    private boolean isAnnotationPresent(VariableElement variableElement, Class<? extends Annotation> annotationClass) {
        return variableElement.getAnnotation(annotationClass) != null;
    }
    
    public String convertDefaultValue(VariableElement variableElement) {
        for(Class<? extends Annotation> aClass : optionalAnnotations.keySet()) {
            if(isAnnotationPresent(variableElement, aClass)) {
                return getDefaultValue(variableElement, aClass);
            }
        }
        
        
        throw new IllegalStateException("ConvertDefaultValue should never be called if !isOptional");
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    private String getDefaultValue(VariableElement variableElement, Class<? extends Annotation> annotationClass) {
        final Function objectFunction = optionalAnnotations.get(annotationClass);
        final Annotation annotation = variableElement.getAnnotation(annotationClass);
        return String.valueOf(objectFunction.apply(annotation));
    }
}
