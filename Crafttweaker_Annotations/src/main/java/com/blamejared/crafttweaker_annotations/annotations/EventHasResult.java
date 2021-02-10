package com.blamejared.crafttweaker_annotations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EventHasResult {
    
    /**
     * if the event has result
     */
    boolean value() default true;
    
    /**
     * what happens if the result is default?
     */
    String defaultDescription() default "";
    
    /**
     * what happens if the result is allow?
     */
    String allowDescription() default "";
    
    /**
     * what happens if the result is deny?
     */
    String denyDescription() default "";
}
