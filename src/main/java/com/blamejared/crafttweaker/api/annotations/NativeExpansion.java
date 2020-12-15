package com.blamejared.crafttweaker.api.annotations;

import org.openzen.zencode.java.ZenCodeType.Expansion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used instead of {@link Expansion} if you don't know the zs name of the type.
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
