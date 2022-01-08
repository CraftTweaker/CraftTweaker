package com.blamejared.crafttweaker.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BracketDumper {
    
    /**
     * The name of the dumped BEP
     */
    String value();
    
    /**
     * The name of the subcommand in CrT
     * Defaults to {@link #value()} (in an attempt to make it plural)
     */
    String subCommandName() default "";
    
    
    /**
     * The filename if exported.
     * Defaults to a lower-cased version {@value}
     */
    String fileName() default "";
    
}
