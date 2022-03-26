package com.blamejared.crafttweaker.api.natives;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Provides information related to a native type registered to ZenCode.
 *
 * <p>Instances of this class can be obtained through a {@link INativeTypeRegistry}, allowing for introspection of which
 * native methods of a specific native class are exposed to scripts.</p>
 *
 * @since 9.1.0
 */
public interface IBakedTypeInfo {
    
    /**
     * Gets the name of the class as it is known by the ZenCode script environment.
     *
     * @return The name of the class as it is known by the ZenCode script environment.
     *
     * @since 9.1.0
     */
    String zenName();
    
    /**
     * Gets a reference to the native class this type information is for.
     *
     * @return A reference to the native class this type information is for.
     *
     * @since 9.1.0
     */
    Class<?> nativeClass();
    
    /**
     * Gets information relative to the specified constructor as exposed to ZenCode, if possible.
     *
     * <p>If the specified constructor is not exposed to ZenCode, then the returned {@link Optional} will be
     * {@linkplain Optional#empty() empty}.</p>
     *
     * @param method The constructor that should be identified.
     *
     * @return An {@link Optional} wrapping the {@linkplain IExecutableReferenceInfo executable information} for the
     * specified constructor, if available; {@linkplain Optional#empty() empty} otherwise.
     *
     * @since 9.1.0
     */
    Optional<IExecutableReferenceInfo> findMethod(final Constructor<?> method);
    
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
    Optional<IExecutableReferenceInfo> findMethod(final Method method);
    
}
