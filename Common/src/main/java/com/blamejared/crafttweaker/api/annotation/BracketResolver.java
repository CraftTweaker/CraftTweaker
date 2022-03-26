package com.blamejared.crafttweaker.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as being responsible for resolving a particular bracket.
 *
 * <p>Methods annotated with this annotation must be both {@code public} and {@code static}. Moreover, they must accept
 * a single parameter of type {@link String} and their return type must be a sub-class of
 * {@link com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable}. The classes containing these methods must
 * also be annotated with {@link ZenRegister} to allow for discovery.</p>
 *
 * <p>The single parameter of the method is a string with the contents of the bracket handler converted to a string and
 * merged together, excluding the bracket handler name. It is up to the method to parse the string as it sees fit. As
 * an example, given a bracket resolver for the handler {@code "item"} and the bracket expression
 * {@code &lt;item:minecraft:dried_kelp&gt;}, the resolver will be invoked with the string
 * {@code "minecraft:dried_kelp"}.</p>
 *
 * <p>If the bracket handler also specifies a {@linkplain BracketValidator validator}, then this method will be invoked
 * only if the validator confirms the validity of the bracket expression, assuming it is possible to do so.</p>
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
public @interface BracketResolver {
    
    /**
     * Gets the name of the bracket handler this resolver is for.
     *
     * @return The name of the bracket handler.
     *
     * @since 9.1.0
     */
    String value();
    
}
