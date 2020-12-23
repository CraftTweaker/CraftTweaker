package com.blamejared.crafttweaker_annotations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NativeTypeRegistration {
    
    /**
     * The expanded class, e.g. IItemStack, Block, ...
     * Can be either a CraftTweaker wrapper or a (registered) vanilla type
     */
    Class<?> value();
    
    /**
     * The name that this type should be registered as
     */
    String zenCodeName();
    
    /**
     * The constructors that should be registered
     */
    NativeConstructor[] constructors() default {};
}
