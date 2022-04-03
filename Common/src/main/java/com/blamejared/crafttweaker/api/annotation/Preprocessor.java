package com.blamejared.crafttweaker.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the class as a preprocessor.
 *
 * <p>Classes annotated with this annotation must implement the
 * {@link com.blamejared.crafttweaker.api.zencode.IPreprocessor} interface. Refer to it for more information related to
 * the various methods that should be implemented.</p>
 *
 * <p>Classes annotated with this annotation must also be annotated with {@link ZenRegister} to allow for automatic
 * discovery and have either a {@code public} no-argument constructor or a {@code public static final} field named
 * {@code INSTANCE}. The field or the constructor, depending on the availability, will then be used by CraftTweaker to
 * automatically create and register the preprocessor. If both a {@code public} no-argument constructor and an instance
 * field is provided, then the one chosen is left to the implementation.</p>
 *
 * <p>It is not necessary for a mod to manually register the preprocessor through a plugin's
 * {@link com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler}: the presence of the
 * annotation along with {@link ZenRegister} is enough for CraftTweaker to automatically discover the class and manage
 * its lifecycle.</p>
 *
 * @since 9.1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Preprocessor {

}

