package com.blamejared.crafttweaker.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark a Method as a bracket handler resolver, the method NEEDS to be {@code public static <type> <methodName>(String bracket)}.
 * <p>
 * The String provided is the exact text inside the bracket {@code <this text here>}, which can then be parsed by the method.
 * <p>
 * The returned Type should implement ICommandStringDisplayable, otherwise it will log a warning at runtime, and an error at compile time if the annotation processors are part of your build Dependencies
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BracketResolver {
    
    /**
     * Gets the String that the bracket handler should match on.
     * I.E. returning "item" will match {@code <item:modid:name>}.
     *
     * @return BracketHandler name
     */
    String value();
    
}
