package com.blamejared.crafttweaker.gametest.framework.annotation.parametized;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterizedGameTest {
    
    Source argumentSource() default @Source();
    
    public @interface Source {
        
        String method() default "";
        
    }
    
}
