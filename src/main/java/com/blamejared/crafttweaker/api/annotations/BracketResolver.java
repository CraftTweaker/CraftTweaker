package com.blamejared.crafttweaker.api.annotations;

import java.lang.annotation.*;

/**
 * Used to mark a Method as a bracket handler resolver, the method NEEDS to be {@code public static <type> <methodName>(String bracket)}.
 * <p>
 * The String provided is the exact text inside the bracket {@code <this text here>}, which can then be parsed by the method.
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
