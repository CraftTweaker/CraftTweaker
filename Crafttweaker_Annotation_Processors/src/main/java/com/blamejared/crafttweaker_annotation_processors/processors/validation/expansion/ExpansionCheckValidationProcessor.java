package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion;

import com.blamejared.crafttweaker_annotation_processors.processors.AbstractCraftTweakerProcessor;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.KnownTypeRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.validator.ExpansionInfoValidator;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import org.openzen.zencode.java.ZenCodeType;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class ExpansionCheckValidationProcessor extends AbstractCraftTweakerProcessor {
    
    private KnownTypeRegistry knownTypeRegistry;
    
    @Override
    public synchronized void performInitialization() {
        knownTypeRegistry = dependencyContainer.getInstanceOfClass(KnownTypeRegistry.class);
    }
    
    @Override
    protected void setupDependencyContainer() {
        super.setupDependencyContainer();
        setupReflections();
    }
    
    private void setupReflections() {
        final ConfigurationBuilder configuration = new ConfigurationBuilder().addUrls(ClasspathHelper
                .forJavaClassPath())
                .addClassLoaders(ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader(), getClass()
                        .getClassLoader())
                .addUrls(ClasspathHelper.forClassLoader())
                .setScanners(new TypeAnnotationsScanner());
        
        final Reflections reflections = new Reflections(configuration);
        dependencyContainer.addInstanceAs(reflections, Reflections.class);
    }
    
    @Override
    public Collection<Class<? extends Annotation>> getSupportedAnnotationClasses() {
        return Arrays.asList(ZenCodeType.Name.class, NativeTypeRegistration.class, ZenCodeType.Expansion.class, TypedExpansion.class);
    }
    
    @Override
    public boolean performProcessing(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
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
