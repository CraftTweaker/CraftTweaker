package com.blamejared.crafttweaker_annotation_processors.processors;

import javax.lang.model.element.*;

public class AnnotationMirrorUtil {
    private AnnotationMirrorUtil(){
    
    }
    
    public static AnnotationMirror getMirror(Element element, String name) {
        return element.getAnnotationMirrors()
                .stream()
                .filter(mirror -> mirror.getAnnotationType()
                        .asElement()
                        .toString()
                        .contentEquals(name))
                .findAny()
                .orElse(null);
    }
    
    public static AnnotationMirror getMirror(Element element, TypeElement typeElement) {
        return getMirror(element, typeElement.getQualifiedName().toString());
    }
    
    public static AnnotationMirror getMirror(Element element, Class<?> cls) {
        return getMirror(element, cls.getCanonicalName());
    }
    
    public static String getAnnotationValue(AnnotationMirror annotationMirror, String name) {
        return getAnnotationValueObject(annotationMirror, name).toString();
    }
    
    public static Object getAnnotationValueObject(AnnotationMirror annotationMirror, String name) {
        return annotationMirror.getElementValues()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getSimpleName().contentEquals(name))
                .map(entry -> entry.getValue().getValue())
                .findAny()
                .orElse(null);
    }
}
