package com.blamejared.crafttweaker.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark a Class as a Preprocessor, this has to be applied to a {@link com.blamejared.crafttweaker.api.zencode.IPreprocessor}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Preprocessor {

}
    
