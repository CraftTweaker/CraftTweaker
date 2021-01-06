package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.validator.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.util.annotations.AnnotationMirrorUtil;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.ExpansionInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class UnsupportedAnnotationValidationRule implements ExpansionInfoValidationRule {
    
    private final Set<Class<? extends Annotation>> unsupportedTypes = new HashSet<>();
    
    
    private final AnnotationMirrorUtil annotationMirrorUtil;
    private final Messager messager;
    
    public UnsupportedAnnotationValidationRule(AnnotationMirrorUtil annotationMirrorUtil, Messager messager) {
        this.annotationMirrorUtil = annotationMirrorUtil;
        this.messager = messager;
        
        fillUnsupportedAnnotations();
        
    }
    
    @Override
    public boolean canValidate(Element enclosedElement) {
        return true;
    }
    
    @Override
    public void validate(Element enclosedElement, ExpansionInfo expansionInfo) {
        getInvalidAnnotationMirrors(enclosedElement).forEach(writeMessage(enclosedElement));
    }
    
    private Stream<AnnotationMirror> getInvalidAnnotationMirrors(Element enclosedElement) {
        return unsupportedTypes.stream()
                .filter(annotationPresentOn(enclosedElement))
                .map(getAnnotationMirrorAt(enclosedElement));
    }
    
    private Function<Class<? extends Annotation>, AnnotationMirror> getAnnotationMirrorAt(Element enclosedElement) {
        return annotationClass -> annotationMirrorUtil.getMirror(enclosedElement, annotationClass);
    }
    
    private Consumer<AnnotationMirror> writeMessage(Element enclosedElement) {
        return annotationMirror -> {
            final String message = "Annotation not allowed in expansions";
            messager.printMessage(Diagnostic.Kind.ERROR, message, enclosedElement, annotationMirror);
        };
    }
    
    private Predicate<Class<? extends Annotation>> annotationPresentOn(Element enclosedElement) {
        return annotationClass -> enclosedElement.getAnnotation(annotationClass) != null;
    }
    
    
    private void fillUnsupportedAnnotations() {
        unsupportedTypes.add(ZenCodeType.Constructor.class);
        unsupportedTypes.add(ZenCodeType.Field.class);
    }
    
}
