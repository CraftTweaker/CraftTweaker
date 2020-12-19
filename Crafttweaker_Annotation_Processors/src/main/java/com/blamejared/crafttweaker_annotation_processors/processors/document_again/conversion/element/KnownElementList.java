package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.element;

import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.DocumentAsType;
import com.blamejared.crafttweaker_annotations.annotations.NativeExpansion;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
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
        return element.getAnnotation(ZenCodeType.Name.class) != null || element.getAnnotation(DocumentAsType.class) != null;
    }
    
    private boolean isElementForExpansionDocumentation(TypeElement element) {
        if(element.getAnnotation(DocumentAsType.class) != null) {
            return false;
        }
        
        return element.getAnnotation(ZenCodeType.Expansion.class) != null || element.getAnnotation(NativeExpansion.class) != null;
        
    }
}
