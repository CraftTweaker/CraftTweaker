package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;

/**
 * Holds information relative to a specific type that still needs to be registered to ZenCode.
 *
 * <p>This record is used as a holder to store all information that CraftTweaker requires to properly create and
 * register a type to the ZenCode environment. The registered types will then be accessible through the
 * {@link IZenClassRegistry}.</p>
 *
 * @param targetName The fully qualified name of the class as it should be exposed to ZenCode, if attempting to register
 *                   a class; otherwise the fully qualified name of the ZenCode type that should be expanded, if
 *                   attempting to register an expansion.
 * @param kind       The {@linkplain TypeKind kind of type} that should be exposed to ZenCode.
 *
 * @since 9.1.0
 */
public record ZenTypeInfo(String targetName, TypeKind kind) {
    
    /**
     * Indicates the kind of type that should be exposed to ZenCode.
     *
     * @since 9.1.0
     */
    public enum TypeKind {
        /**
         * Marks the type as a regular class that should be exposed to ZenCode.
         *
         * @since 9.1.0
         */
        CLASS,
        /**
         * Marks the type as an expansion for the {@code targetName}.
         *
         * @since 9.1.0
         */
        EXPANSION
    }
    
    /**
     * Converts a {@link NativeTypeInfo} into a {@link ZenTypeInfo} for ZenCode registration.
     *
     * @param nativeInfo The native type info to convert.
     *
     * @return A {@link ZenTypeInfo} which can be used to expose the given native type to ZenCode.
     *
     * @since 9.1.0
     */
    public static ZenTypeInfo from(final NativeTypeInfo nativeInfo) {
        
        return new ZenTypeInfo(nativeInfo.name(), TypeKind.EXPANSION);
    }
    
}
