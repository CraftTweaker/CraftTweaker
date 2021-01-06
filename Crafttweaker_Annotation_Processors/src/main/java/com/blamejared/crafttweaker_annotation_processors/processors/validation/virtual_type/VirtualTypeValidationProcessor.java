package com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type;

import com.blamejared.crafttweaker_annotation_processors.processors.AbstractCraftTweakerProcessor;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type.validator.VirtualTypeValidator;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class VirtualTypeValidationProcessor extends AbstractCraftTweakerProcessor {
    
    private VirtualTypeValidator virtualTypeValidator;
    
    @Override
    public Collection<Class<? extends Annotation>> getSupportedAnnotationClasses() {
        return Collections.singleton(ZenCodeType.Name.class);
    }
    
    @Override
    public synchronized void performInitialization() {
        virtualTypeValidator = dependencyContainer.getInstanceOfClass(VirtualTypeValidator.class);
    }
    
    @Override
    public boolean performProcessing(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        virtualTypeValidator.validateAll(roundEnv.getElementsAnnotatedWith(ZenCodeType.Name.class));
        return false;
    }
}
