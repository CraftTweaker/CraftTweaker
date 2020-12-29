package com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type;

import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.SingletonDependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type.validator.VirtualTypeValidator;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Collections;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class VirtualTypeValidationProcessor extends AbstractProcessor {
    
    private final DependencyContainer dependencyContainer = new SingletonDependencyContainer();
    private VirtualTypeValidator virtualTypeValidator;
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ZenCodeType.Name.class.getCanonicalName());
    }
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        setupDependencyContainer();
        virtualTypeValidator = dependencyContainer.getInstanceOfClass(VirtualTypeValidator.class);
    }
    
    private void setupDependencyContainer() {
        dependencyContainer.addInstanceAs(dependencyContainer, DependencyContainer.class);
        dependencyContainer.addInstanceAs(processingEnv, ProcessingEnvironment.class);
        dependencyContainer.addInstanceAs(processingEnv.getTypeUtils(), Types.class);
        dependencyContainer.addInstanceAs(processingEnv.getElementUtils(), Elements.class);
        dependencyContainer.addInstanceAs(processingEnv.getMessager(), Messager.class);
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        virtualTypeValidator.validateAll(roundEnv.getElementsAnnotatedWith(ZenCodeType.Name.class));
        return false;
    }
}
