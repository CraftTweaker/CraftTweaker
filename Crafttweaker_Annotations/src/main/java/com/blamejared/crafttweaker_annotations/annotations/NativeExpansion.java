package com.blamejared.crafttweaker_annotations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used instead of org.openzen.zencode.java.ZenCodeType.Expansion if you don't know the zs name of the type.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NativeExpansion {
    
    /**
     * The expanded class, e.g. IItemStack, Block, ...
     * Can be either a CraftTweaker wrapper or a (registered) vanilla type
     */
    Class<?> value();
}
