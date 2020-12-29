package com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type.validator.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.util.annotations.AnnotationMirrorUtil;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ModifierValidationRule implements VirtualTypeValidationRule {
    
    private final Messager messager;
    private final AnnotationMirrorUtil annotationMirrorUtil;
    private final Set<Class<? extends Annotation>> checkedAnnotations = new HashSet<>();
    private final Set<Class<? extends Annotation>> virtualOnlyAnnotations = new HashSet<>();
    
    public ModifierValidationRule(Messager messager, AnnotationMirrorUtil annotationMirrorUtil) {
        this.messager = messager;
        this.annotationMirrorUtil = annotationMirrorUtil;
        
        fillCheckedAnnotations();
        fillVirtualOnlyAnnotations();
    }
    
    private void fillVirtualOnlyAnnotations() {
        virtualOnlyAnnotations.add(ZenCodeType.Operator.class);
        virtualOnlyAnnotations.add(ZenCodeType.Getter.class);
        virtualOnlyAnnotations.add(ZenCodeType.Setter.class);
        virtualOnlyAnnotations.add(ZenCodeType.Caster.class);
        virtualOnlyAnnotations.add(ZenCodeType.Constructor.class);
    }
    
    private void fillCheckedAnnotations() {
        checkedAnnotations.add(ZenCodeType.Method.class);
        checkedAnnotations.add(ZenCodeType.Constructor.class);
        checkedAnnotations.add(ZenCodeType.Field.class);
    }
    
    @Override
    public boolean canValidate(Element enclosedElement) {
        return hasZenAnnotation(enclosedElement);
    }
    
    private boolean hasZenAnnotation(Element typeElement) {
        return hasVirtualOnlyAnnotation(typeElement) || hasCheckedAnnotation(typeElement);
    }
    
    private boolean hasVirtualOnlyAnnotation(Element typeElement) {
        return virtualOnlyAnnotations.stream().anyMatch(annotationPresentOn(typeElement));
    }
    
    private boolean hasCheckedAnnotation(Element typeElement) {
        return checkedAnnotations.stream().anyMatch(annotationPresentOn(typeElement));
    }
    
    private Predicate<Class<? extends Annotation>> annotationPresentOn(Element typeElement) {
        return annotationClass -> typeElement.getAnnotation(annotationClass) != null;
    }
    
    @Override
    public void validate(Element enclosedElement) {
        if(notPublic(enclosedElement)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "All zenMembers must be public", enclosedElement);
        }
        if(notVirtual(enclosedElement)) {
            getVirtualOnlyMirror(enclosedElement).forEach(mirror -> {
                final String message = "Annotation requires instance member";
                messager.printMessage(Diagnostic.Kind.ERROR, message, enclosedElement, mirror);
            });
        }
    }
    
    private boolean notPublic(Element typeElement) {
        return !typeElement.getModifiers().contains(Modifier.PUBLIC);
    }
    
    private boolean notVirtual(Element typeElement) {
        return hasVirtualOnlyAnnotation(typeElement) && isStatic(typeElement);
    }
    
    private boolean isStatic(Element typeElement) {
        return typeElement.getModifiers().contains(Modifier.STATIC);
    }
    
    private Stream<AnnotationMirror> getVirtualOnlyMirror(Element typeElement) {
        return virtualOnlyAnnotations.stream()
                .filter(annotationPresentOn(typeElement))
                .map(annotation -> annotationMirrorUtil.getMirror(typeElement, annotation));
    }
}
