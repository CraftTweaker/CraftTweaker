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
    
}
