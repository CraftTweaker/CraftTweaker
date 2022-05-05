package com.blamejared.crafttweaker_annotations.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface NativeConstructor {
    
    ConstructorParameter[] value();
    
    String description() default "";
    
    String deprecationMessage() default "";
    
    String getSinceVersion() default "";
    
    @interface ConstructorParameter {
        
        Class<?> type();
        
        String name();
        
        String description() default "No description provided";
        
        String[] examples() default {};
        
    }
    
}
