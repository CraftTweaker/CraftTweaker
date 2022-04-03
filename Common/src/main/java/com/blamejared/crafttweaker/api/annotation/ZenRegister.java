package com.blamejared.crafttweaker.api.annotation;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as requiring registration to ZenCode.
 *
 * <p>Classes with this annotation will be automatically discovered by CraftTweaker and their lifecycle managed
 * accordingly. It is thus not required for a mod to use a plugin's
 * {@link com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler} to register the classes.</p>
 *
 * <p>Classes will be automatically registered to the
 * {@linkplain com.blamejared.crafttweaker.api.zencode.IScriptLoader loaders} specified in {@link #loaders()}. It is
 * also possible to prevent discovery of this class through the {@link #modDeps()} annotation parameter.</p>
 *
 * <p>It might be required to combine this annotation with other annotations, such as
 * {@link org.openzen.zencode.java.ZenCodeType.Name} or
 * {@link com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration} depending on the circumstances.
 * Refer to the other annotations documentation for more information.</p>
 *
 * @since 9.1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ZenRegister {
    
    /**
     * Specifies a list of mods that must be present for discovery of the class to occur.
     *
     * <p>In other words, if any of the mods specified in the array is not available in the current environment, the
     * class will not be registered to ZenCode. This allows for soft-dependencies to be correctly handled by mods.</p>
     *
     * <p>If no dependency is specified (i.e. the array is left empty), the class will always be loaded.</p>
     *
     * @return The mod IDs that need to be available for this class to be discovered.
     *
     * @since 9.1.0
     */
    String[] modDeps() default {};
    
    /**
     * Returns a list of {@linkplain com.blamejared.crafttweaker.api.zencode.IScriptLoader loaders} to which this class
     * should be registered.
     *
     * <p>Any loader that is not specified in this array will not be able to reference this class in ZenCode scripts,
     * effectively allowing for isolation of multiple loaders.</p>
     *
     * <p>By default, all classes will be registered to the default loader, whose name is identified by
     * {@link CraftTweakerConstants#DEFAULT_LOADER_NAME}.</p>
     *
     * <p>Although discouraged, it is possible to indicate that a class should be registered to all available loaders
     * by using the {@linkplain CraftTweakerConstants#ALL_LOADERS_MARKER global marker}. Note that this should be used
     * sporadically to prevent pollution of the global namespace and unwanted side effects.</p>
     *
     * @return The loaders to which this class should be available.
     *
     * @since 9.1.0
     */
    String[] loaders() default {CraftTweakerConstants.DEFAULT_LOADER_NAME};
    
}
