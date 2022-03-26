package com.blamejared.crafttweaker.api.natives;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

/**
 * Represents a registry holding information for native types known to the ZenCode environment.
 *
 * @since 9.1.0
 */
public interface INativeTypeRegistry {
    
    /**
     * Tries to find the name a native class is exposed as to ZenCode.
     *
     * @param clazz The class whose name should be looked up.
     *
     * @return An {@link Optional} wrapping the name if the class is registered, empty otherwise.
     *
     * @since 9.1.0
     */
    Optional<String> getZenNameFor(final Class<?> clazz);
    
    /**
     * Gets a {@link Collection} of all the registered {@link IBakedTypeInfo} instances.
     *
     * <p>The returned collection may not be modified by the caller.</p>
     *
     * @return A collection with all known type information.
     *
     * @since 9.1.0
     */
    Collection<IBakedTypeInfo> getBakedTypeInfo();
    
    /**
     * Obtains the {@linkplain IBakedTypeInfo type information} for the specified class, if registered to ZenCode.
     *
     * @param clazz The class whose information should be obtained.
     *
     * @return An {@link Optional} either wrapping the information if available, or empty if the class is not known.
     *
     * @since 9.1.0
     */
    Optional<IBakedTypeInfo> getBakedTypeInfoFor(final Class<?> clazz);
    
    /**
     * Gets information relative to the specified constructor as exposed to ZenCode, if possible.
     *
     * <p>If the specified constructor is not exposed to ZenCode, then the returned {@link Optional} will be
     * {@linkplain Optional#empty() empty}.</p>
     *
     * @param constructor The constructor that should be identified.
     *
     * @return An {@link Optional} wrapping the {@linkplain IExecutableReferenceInfo executable information} for the
     * specified constructor, if available; {@linkplain Optional#empty() empty} otherwise.
     *
     * @since 9.1.0
     */
    Optional<IExecutableReferenceInfo> getExecutableReferenceInfoFor(final Constructor<?> constructor);
    
    /**
     * Gets information relative to the specified method as exposed to ZenCode, if possible.
     *
     * <p>If the specified method is not exposed to ZenCode, then the returned {@link Optional} will be
     * {@linkplain Optional#empty() empty}.</p>
     *
     * @param method The method that should be identified.
     *
     * @return An {@link Optional} wrapping the {@linkplain IExecutableReferenceInfo executable information} for the
     * specified method, if available; {@linkplain Optional#empty() empty} otherwise.
     *
     * @since 9.1.0
     */
    Optional<IExecutableReferenceInfo> getExecutableReferenceInfoFor(final Method method);
    
}
