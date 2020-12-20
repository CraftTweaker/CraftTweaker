package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotations.annotations.Document;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Collection;
import java.util.HashSet;

public class AnnotatedElementCollection {
    private final Collection<Element> allElements = new HashSet<>();
    private final ProcessingEnvironment processingEnv;
    
    
    public AnnotatedElementCollection(ProcessingEnvironment processingEnv) {
        
        this.processingEnv = processingEnv;
    }
    
    public void addRound(RoundEnvironment roundEnv) {
        allElements.addAll(roundEnv.getElementsAnnotatedWith(Document.class));
    }
    
    public void handleElements() {
        allElements.stream().map(Object::toString) //Get names
                .map(processingEnv.getElementUtils()::getTypeElement).forEach(this::handleElement);
    }
    
    private void handleElement(TypeElement typeElement) {
        final Document document = typeElement.getAnnotation(Document.class);
        if(document == null) {
            this.processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error! Document annotation null", typeElement);
            return;
        }
        
        if(!typeElement.getKind().isClass() && !typeElement.getKind().isInterface()) {
            this.processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "How is this annotated", typeElement);
        }
    }
}
