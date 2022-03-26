package com.blamejared.crafttweaker.api.natives;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * Represents information related to a specific native executable reference registered to ZenCode.
 *
 * <p>An executable reference is the general term to refer to both native methods and constructors as exposed to
 * ZenCode.</p>
 *
 * @since 9.1.0
 */
public interface IExecutableReferenceInfo {
    
    /**
     * Attempts to grab information relative to the given annotation class, if available.
     *
     * @param annotationClass The class of the annotation that should be queried.
     * @param <T>             The type of the annotation to identify.
     *
     * @return A reference to the given annotation if possible, {@link Optional#empty()} otherwise.
     *
     * @apiNote The API is only guaranteed to return meaningful results when used with ZenCode specific annotations.
     * Attempting to query any other annotation is undefined behavior and might or might not return meaningful results.
     * @since 9.1.0
     */
    <T extends Annotation> Optional<T> getAnnotation(final Class<T> annotationClass);
    
}
