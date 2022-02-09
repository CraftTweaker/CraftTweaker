package com.blamejared.crafttweaker.api.annotation;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to automatically register a class to the ZenScript Scripting Engine, should be combined with {@link org.openzen.zencode.java.ZenCodeType.Name}.
 * <p>
 * set {@link ZenRegister#modDeps()} if this class should only be loaded if another mod is present, value is an array of modids of the required mods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ZenRegister {
    
    /**
     * Array of modids that are required for this class to be loaded, empty String or array for no dependencies.
     *
     * @return array of modids.
     */
    String[] modDeps() default "";
    
    String loader() default CraftTweakerConstants.DEFAULT_LOADER_NAME;
    
}
