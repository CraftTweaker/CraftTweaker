package com.blamejared.crafttweaker_annotation_processors.processors.validation.parameter;

import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.SingletonDependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.parameter.validator.ParameterValidator;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ParameterValidationProcessor extends AbstractProcessor {
    
    private final DependencyContainer dependencyContainer = new SingletonDependencyContainer();
    private ParameterValidator parameterValidator;
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> result = new HashSet<>();
        addSupportedOptionalAnnotations(result);
        addSupportedUnsignedAnnotations(result);
        addSupportedNullableAnnotations(result);
        return result;
    }
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        setupDependencyContainer();
        
        parameterValidator = dependencyContainer.getInstanceOfClass(ParameterValidator.class);
    }
    
    private void setupDependencyContainer() {
        dependencyContainer.addInstanceAs(dependencyContainer, DependencyContainer.class);
        dependencyContainer.addInstanceAs(processingEnv, ProcessingEnvironment.class);
        dependencyContainer.addInstanceAs(processingEnv.getTypeUtils(), Types.class);
        dependencyContainer.addInstanceAs(processingEnv.getElementUtils(), Elements.class);
        dependencyContainer.addInstanceAs(processingEnv.getMessager(), Messager.class);
    }
    
    private void addSupportedUnsignedAnnotations(Set<String> result) {
        addSupportedAnnotationType(result, ZenCodeType.USize.class);
        addSupportedAnnotationType(result, ZenCodeType.Unsigned.class);
    }
    
    private void addSupportedNullableAnnotations(Set<String> result) {
        addSupportedAnnotationType(result, ZenCodeType.Nullable.class);
        addSupportedAnnotationType(result, ZenCodeType.NullableUSize.class);
    }
    
    private void addSupportedOptionalAnnotations(Set<String> result) {
        addSupportedAnnotationType(result, ZenCodeType.Optional.class);
        addSupportedAnnotationType(result, ZenCodeType.OptionalInt.class);
        addSupportedAnnotationType(result, ZenCodeType.OptionalLong.class);
        addSupportedAnnotationType(result, ZenCodeType.OptionalFloat.class);
        addSupportedAnnotationType(result, ZenCodeType.OptionalString.class);
        addSupportedAnnotationType(result, ZenCodeType.OptionalDouble.class);
    }
    
    private void addSupportedAnnotationType(Set<String> result, Class<? extends Annotation> annotationClass) {
        result.add(annotationClass.getCanonicalName());
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        
        annotations.stream()
                .flatMap(type -> roundEnv.getElementsAnnotatedWith(type).stream())
                .forEach(parameterValidator::validate);
        
        return false;
    }
}
