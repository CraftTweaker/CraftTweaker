package com.blamejared.crafttweaker_annotation_processors.processors.util.annotations;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Predicate;

public class AnnotationMirrorUtil {
    
    public boolean isAnnotationPresentOn(Element element, String annotationName) {
        return tryGetMirror(element, annotationName).isPresent();
    }
    
    public AnnotationMirror getMirror(Element element, String annotationName) {
        return tryGetMirror(element, annotationName).orElseThrow(() -> new IllegalArgumentException("Element has no annotation " + annotationName));
    }
    
    @Nonnull
    public Optional<? extends AnnotationMirror> tryGetMirror(Element element, String annotationName) {
        return element.getAnnotationMirrors()
                .stream()
                .filter(annotationTypeIs(annotationName))
                .findAny();
    }
    
    
    private Predicate<AnnotationMirror> annotationTypeIs(String name) {
        return mirror -> mirror.getAnnotationType().asElement().toString().contentEquals(name);
    }
    
    public AnnotationMirror getMirror(Element element, TypeElement typeElement) {
        return getMirror(element, typeElement.getQualifiedName().toString());
    }
    
    public AnnotationMirror getMirror(Element element, Class<? extends Annotation> annotationClass) {
        return getMirror(element, annotationClass.getCanonicalName());
    }
    
    public String getAnnotationValue(AnnotationMirror annotationMirror, String name) {
        return getAnnotationValueObject(annotationMirror, name).toString();
    }
    
    public Object getAnnotationValueObject(AnnotationMirror annotationMirror, String name) {
        return tryGetAnnotationValue(annotationMirror, name).orElseThrow(() -> new IllegalArgumentException("Could not get value " + name));
    }
    
    @Nonnull
    private Optional<Object> tryGetAnnotationValue(AnnotationMirror annotationMirror, String name) {
        return annotationMirror.getElementValues()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getSimpleName().contentEquals(name))
                .map(entry -> entry.getValue().getValue())
                .findAny();
    }
}
