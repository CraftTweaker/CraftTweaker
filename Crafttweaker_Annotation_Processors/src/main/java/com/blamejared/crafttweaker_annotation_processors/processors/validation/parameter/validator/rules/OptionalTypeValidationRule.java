package com.blamejared.crafttweaker_annotation_processors.processors.validation.parameter.validator.rules;

import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class OptionalTypeValidationRule implements ParameterValidationRule {
    
    private final Messager messager;
    private final Elements elementUtils;
    private final Types typeUtils;
    
    private final Map<TypeMirror, Class<? extends Annotation>> optionalAnnotations = new HashMap<>();
    
    public OptionalTypeValidationRule(Messager messager, Elements elementUtils, Types typeUtils) {
        this.messager = messager;
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        
        
        fillAnnotationMap();
    }
    
    private void fillAnnotationMap() {
        putAnnotation(TypeKind.INT, ZenCodeType.OptionalInt.class);
        putAnnotation(TypeKind.LONG, ZenCodeType.OptionalLong.class);
        putAnnotation(TypeKind.FLOAT, ZenCodeType.OptionalFloat.class);
        putAnnotation(TypeKind.DOUBLE, ZenCodeType.OptionalDouble.class);
        
        putAnnotation(Object.class, ZenCodeType.Optional.class);
        putAnnotation(String.class, ZenCodeType.OptionalString.class);
    }
    
    private void putAnnotation(Class<?> cls, Class<? extends Annotation> annotationClass) {
        final TypeElement typeElement = elementUtils.getTypeElement(cls.getCanonicalName());
        optionalAnnotations.put(typeElement.asType(), annotationClass);
    }
    
    private void putAnnotation(TypeKind typeKind, Class<? extends Annotation> annotationClass) {
        final PrimitiveType primitiveType = typeUtils.getPrimitiveType(typeKind);
        optionalAnnotations.put(primitiveType, annotationClass);
    }
    
    
    @Override
    public boolean canValidate(VariableElement parameter) {
        return isOptional(parameter);
    }
    
    private boolean isOptional(VariableElement parameter) {
        return optionalAnnotations.values().stream().anyMatch(annotationPresentOn(parameter));
    }
    
    private Predicate<Class<? extends Annotation>> annotationPresentOn(VariableElement parameter) {
        return annotationClass -> parameter.getAnnotation(annotationClass) != null;
    }
    
    @Override
    public void validate(VariableElement parameter) {
        if(shouldParameterUseSpecialAnnotation(parameter)) {
            checkIfParameterUsesProperAnnotation(parameter);
        }
    }
    
    private boolean shouldParameterUseSpecialAnnotation(VariableElement parameter) {
        return optionalAnnotations.containsKey(parameter.asType());
    }
    
    private void checkIfParameterUsesProperAnnotation(VariableElement parameter) {
        final Class<? extends Annotation> annotationClass = optionalAnnotations.get(parameter.asType());
        if(parameter.getAnnotation(annotationClass) == null) {
            final String message = "Optional type should use " + annotationClass.getSimpleName() + " annotation";
            messager.printMessage(Diagnostic.Kind.ERROR, message, parameter);
        }
    }
}
