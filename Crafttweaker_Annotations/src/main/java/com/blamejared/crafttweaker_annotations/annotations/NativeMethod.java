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
    
    Class<?>[] parameters();
    
    String getterName() default "";
    
    String setterName() default "";
    
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Holder {
        
        NativeMethod[] value();
        
    }
    
}
