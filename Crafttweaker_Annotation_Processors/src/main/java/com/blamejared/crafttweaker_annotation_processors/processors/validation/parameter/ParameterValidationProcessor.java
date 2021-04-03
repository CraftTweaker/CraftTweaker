package com.blamejared.crafttweaker_annotation_processors.processors.validation.parameter;

import com.blamejared.crafttweaker_annotation_processors.processors.AbstractCraftTweakerProcessor;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.parameter.validator.ParameterValidator;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ParameterValidationProcessor extends AbstractCraftTweakerProcessor {
    
    private ParameterValidator parameterValidator;
    
    @Override
    protected void performInitialization() {
        parameterValidator = dependencyContainer.getInstanceOfClass(ParameterValidator.class);
    }
    
    @Override
    public Collection<Class<? extends Annotation>> getSupportedAnnotationClasses() {
        final Set<Class<? extends Annotation>> result = new HashSet<>();
        addSupportedOptionalAnnotations(result);
        addSupportedUnsignedAnnotations(result);
        addSupportedNullableAnnotations(result);
        return result;
    }
    
    private void addSupportedUnsignedAnnotations(Set<Class<? extends Annotation>> result) {
        result.add(ZenCodeType.USize.class);
        result.add(ZenCodeType.Unsigned.class);
    }
    
    private void addSupportedNullableAnnotations(Set<Class<? extends Annotation>> result) {
        result.add(ZenCodeType.Nullable.class);
        result.add(ZenCodeType.NullableUSize.class);
    }
    
    private void addSupportedOptionalAnnotations(Set<Class<? extends Annotation>> result) {
        result.add(ZenCodeType.Optional.class);
        result.add(ZenCodeType.OptionalInt.class);
        result.add(ZenCodeType.OptionalLong.class);
        result.add(ZenCodeType.OptionalFloat.class);
        result.add(ZenCodeType.OptionalString.class);
        result.add(ZenCodeType.OptionalDouble.class);
        result.add(ZenCodeType.OptionalBoolean.class);
        result.add(ZenCodeType.OptionalChar.class);
    }
    
    @Override
    public boolean performProcessing(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        
        annotations.stream()
                .flatMap(type -> roundEnv.getElementsAnnotatedWith(type).stream())
                .forEach(parameterValidator::validate);
        
        return false;
    }
}
