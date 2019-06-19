package com.blamejared.crafttweaker.api.annotations;

/**
 * Used to automatically register a class to the ZenScript Scripting Engine, should be combined with {@link org.openzen.zencode.java.ZenCodeType.Name}.
 * <p>
 * set {@link ZenRegister#modDeps()} if this class should only be loaded if another mod is present, value is an array of modids of the required mods.
 */
public @interface ZenRegister {
    
    /**
     * Array of modids that are required for this class to be loaded, empty String or array for no dependencies.
     *
     * @return array of modids.
     */
    String[] modDeps() default "";
}
