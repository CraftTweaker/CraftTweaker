package com.blamejared.crafttweaker.api.natives;

import java.lang.annotation.Annotation;
import java.util.Optional;

public interface IExecutableReferenceInfo {
    
    <T extends Annotation> Optional<T> getAnnotation(final Class<T> annotationClass);
    
}
