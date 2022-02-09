package com.blamejared.crafttweaker.api.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BracketValidator {
    
    String value();
    
    boolean validatesCustom() default false;
    
}
