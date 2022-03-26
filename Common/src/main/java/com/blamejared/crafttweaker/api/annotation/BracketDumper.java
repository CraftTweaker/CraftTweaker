package com.blamejared.crafttweaker.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as being responsible for dumping all valid values for a specific bracket handler.
 *
 * <p>If not all values can be dumped, then the method should be responsible for providing the biggest subset of valid
 * values possible. This ensures that the information provided by the dumper is complete.</p>
 *
 * <p>Methods annotated by this annotation <strong>must</strong> be both {@code public} and {@code static}. They must
 * have no parameters and return a {@link java.util.Collection} of {@link String}s (i.e {@code Collection<String>}).
 * Moreover, the classes where they are declared must be annotated with {@link ZenRegister}.</p>
 *
 * <p>It is not necessary for a mod to manually register a method annotated with this annotation through a plugin's
 * {@link com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler}: using the annotation as described
 * above is sufficient for CraftTweaker to handle registration manually.</p>
 *
 * @since 9.1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BracketDumper {
    
    /**
     * Indicates the name of bracket handler for which this dumper is for.
     *
     * @return The bracket handler for which this dumper is for.
     *
     * @since 9.1.0
     */
    String value();
    
    /**
     * Gets the name of the sub command used in {@code ct dump} to invoke this dumper.
     *
     * <p>If no value or an empty string is specified, then CraftTweaker will attempt to pluralize the string returned
     * by {@link #value()}.</p>
     *
     * @return The name of the sub command to invoke this dumper.
     *
     * @since 9.1.0
     */
    String subCommandName() default "";
    
    /**
     * Gets the name of the file where data will be dumped to if requested.
     *
     * <p>If no value or an empty string is specified, then CraftTweaker will use the string returned by
     * {@link #value()} as the file name instead.</p>
     *
     * @return The name of the file where data will be dumped to.
     *
     * @since 9.1.0
     */
    String fileName() default "";
    
}
