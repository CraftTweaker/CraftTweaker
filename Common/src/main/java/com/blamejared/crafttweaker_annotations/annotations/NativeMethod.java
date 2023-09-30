package com.blamejared.crafttweaker_annotations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Repeatable(NativeMethod.Holder.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NativeMethod {
    
    String name();
    
    MethodParameter[] parameters();
    
    String getterName() default "";
    
    String setterName() default "";
    
    @interface MethodParameter {
        
        Class<?> type();
        
        String name();
        
        String description() default "";
        
        String[] examples() default {};
        
    }
    
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Holder {
        
        NativeMethod[] value();
        
    }
    
}
