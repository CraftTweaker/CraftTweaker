package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion;

import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.SingletonDependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.KnownTypeRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.validator.ExpansionInfoValidator;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import org.openzen.zencode.java.ZenCodeType;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ExpansionCheckValidationProcessor extends AbstractProcessor {
    
    private final DependencyContainer dependencyContainer = new SingletonDependencyContainer();
    private KnownTypeRegistry knownTypeRegistry;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        setupDependencyContainer();
        setupReflections();
        knownTypeRegistry = dependencyContainer.getInstanceOfClass(KnownTypeRegistry.class);
    }
    
    private void setupDependencyContainer() {
        dependencyContainer.addInstanceAs(dependencyContainer, DependencyContainer.class);
        
        dependencyContainer.addInstanceAs(processingEnv, ProcessingEnvironment.class);
        dependencyContainer.addInstanceAs(processingEnv.getMessager(), Messager.class);
        dependencyContainer.addInstanceAs(processingEnv.getElementUtils(), Elements.class);
        dependencyContainer.addInstanceAs(processingEnv.getTypeUtils(), Types.class);
    }
    
    private void setupReflections() {
        final ConfigurationBuilder configuration = new ConfigurationBuilder().addUrls(ClasspathHelper
                .forJavaClassPath())
                .addClassLoaders(ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader(), getClass()
                        .getClassLoader())
                .addUrls(ClasspathHelper.forClassLoader());
        
        final Reflections reflections = new Reflections(configuration);
        dependencyContainer.addInstanceAs(reflections, Reflections.class);
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final HashSet<String> result = new HashSet<>();
        addSupportedAnnotationType(result, ZenCodeType.Name.class);
        addSupportedAnnotationType(result, NativeTypeRegistration.class);
        addSupportedAnnotationType(result, ZenCodeType.Expansion.class);
        addSupportedAnnotationType(result, TypedExpansion.class);
        return result;
    }
    
    private void addSupportedAnnotationType(Set<String> result, Class<? extends Annotation> annotationClass) {
        result.add(annotationClass.getCanonicalName());
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(roundEnv.processingOver()) {
            handleLastRound();
        } else {
            handleIntermediateRound(roundEnv);
        }
        
        return false;
    }
    
    private void handleIntermediateRound(RoundEnvironment roundEnv) {
        knownTypeRegistry.addNamedTypes(roundEnv.getElementsAnnotatedWith(ZenCodeType.Name.class));
        knownTypeRegistry.addNativeTypes(roundEnv.getElementsAnnotatedWith(NativeTypeRegistration.class));
        knownTypeRegistry.addExpansionTypes(roundEnv.getElementsAnnotatedWith(ZenCodeType.Expansion.class));
        knownTypeRegistry.addTypedExpansionTypes(roundEnv.getElementsAnnotatedWith(TypedExpansion.class));
    }
    
    private void handleLastRound() {
        final ExpansionInfoValidator validator = dependencyContainer.getInstanceOfClass(ExpansionInfoValidator.class);
        validator.validateAll();
    }
}
