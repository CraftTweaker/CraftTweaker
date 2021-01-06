package com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.element;

import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class KnownElementList {
    
    private final ProcessingEnvironment processingEnv;
    private Collection<TypeElement> knownElements;
    
    public KnownElementList(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        this.knownElements = new HashSet<>();
    }
    
    private void updateElements() {
        knownElements = knownElements.stream()
                .map(Object::toString)
                .map(processingEnv.getElementUtils()::getTypeElement)
                .collect(Collectors.toSet());
    }
    
    public void addAllForIntermediateRound(RoundEnvironment roundEnvironment) {
        final Set<TypeElement> elementsOfThisRound = roundEnvironment.getElementsAnnotatedWith(Document.class)
                .stream()
                .map(element -> (TypeElement) element)
                .collect(Collectors.toSet());
        this.knownElements.addAll(elementsOfThisRound);
    }
    
    public Collection<TypeElement> getElementsForTypeDocumentation() {
        updateElements();
        
        return knownElements.stream()
                .filter(this::isElementForTypeDocumentation)
                .collect(Collectors.toList());
    }
    
    public Collection<TypeElement> getElementsForExpansionDocumentation() {
        updateElements();
        
        return knownElements.stream()
                .filter(this::isElementForExpansionDocumentation)
                .collect(Collectors.toList());
    }
    
    private boolean isElementForTypeDocumentation(TypeElement element) {
        return isAnnotationPresent(element, ZenCodeType.Name.class) || isAnnotationPresent(element, NativeTypeRegistration.class);
    }
    
    private boolean isElementForExpansionDocumentation(TypeElement element) {
        return isAnnotationPresent(element, ZenCodeType.Expansion.class) || isAnnotationPresent(element, TypedExpansion.class);
    }
    
    private boolean isAnnotationPresent(Element element, Class<? extends Annotation> annotation) {
        return element.getAnnotation(annotation) != null;
    }
}
