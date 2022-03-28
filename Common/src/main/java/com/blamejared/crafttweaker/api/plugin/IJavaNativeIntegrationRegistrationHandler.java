package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;

/**
 * Handles registration of the various components required to integrate ZenCode scripts and Java code.
 *
 * <p>The various integration components are made up of native classes, zen classes, globals, and preprocessors. Refer
 * to each specific method for more information.</p>
 *
 * @since 9.1.0
 */
public interface IJavaNativeIntegrationRegistrationHandler {
    
    /**
     * Registers the specified class as a native type for ZenCode.
     *
     * <p>A native type is a pre-existing class that has not been written with ZenCode scripts in mind, but that is
     * being exposed to scripts regardless to allow for easier usage. An example of such a class might be
     * {@code net.minecraft.world.level.block.Block}.</p>
     *
     * <p>ZenCode sees native types as classes with the members specified by the given {@link NativeTypeInfo} and that
     * are automatically expanded by the class specified in the registration. An expansion, even if empty, is
     * <strong>required</strong> to be able to register a native type to ZenCode. It is thus suggested to use expansion
     * constructs as much as possible instead of specifying a list of methods in the type information to expose methods
     * for ease of usage and discovery.</p>
     *
     * @param loader The name of the loader in which the specified native type should be available in.
     * @param clazz  The {@link Class} that acts as the expansion necessary to register the native type. This class
     *               <strong>is not</strong> the same class returned by {@link NativeTypeInfo#targetedType()}.
     * @param info   A {@link NativeTypeInfo} record holding all information related to the native type.
     *
     * @since 9.1.0
     */
    void registerNativeType(final String loader, final Class<?> clazz, final NativeTypeInfo info);
    
    /**
     * Registers the specified class as a class for ZenCode.
     *
     * <p>A class is simply considered a normal class in ZenCode. Refer to {@link ZenTypeInfo} for the different kinds
     * of classes that can be registered.</p>
     *
     * @param loader The name of the loader in which the specified class should be available in.
     * @param clazz  The {@link Class} that should be registered to ZenCode.
     * @param info   A {@link ZenTypeInfo} record holding all information related to the {@code clazz} that ZenCode
     *               requires to properly register it.
     *
     * @since 9.1.0
     */
    void registerZenClass(final String loader, final Class<?> clazz, final ZenTypeInfo info);
    
    /**
     * Marks a class as having globals that should be registered to ZenCode.
     *
     * <p>A global is a name that is globally available and can be referenced anywhere in the script, such as
     * {@code loadedMods} or {@code println}. The usage of globals is discouraged as a matter of code cleanliness: too
     * many globals could simply lead to global scope pollution.</p>
     *
     * @param loader The name of the loader in which the globals should be available in. The class must have already
     *               been registered to the same loader prior through
     *               {@link #registerZenClass(String, Class, ZenTypeInfo)}.
     * @param clazz  The {@link Class} housing the globals that should be registered to ZenCode.
     * @param info   A {@link ZenTypeInfo} record holding all information related to the {@code clazz} that ZenCode
     *               requires to properly register it.
     *
     * @since 9.1.0
     */
    void registerGlobalsIn(final String loader, final Class<?> clazz, final ZenTypeInfo info);
    
    /**
     * Registers the given preprocessor to ZenCode.
     *
     * <p>Each preprocessor is identified by a unique name. Refer to {@link IPreprocessor} for more information.</p>
     *
     * @param preprocessor The preprocessor to register.
     *
     * @since 9.1.0
     */
    void registerPreprocessor(final IPreprocessor preprocessor);
    
}
