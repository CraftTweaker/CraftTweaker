package com.blamejared.crafttweaker.api.managers;

import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

/**
 * Default interface for Registry based handlers as they can all remove recipes by ResourceLocation.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.registries.IRegistryManager")
public interface IRegistryManager {
    
    /**
     * Remove recipe based on Registry name
     *
     * @param name registry name of recipe to remove
     */
    @ZenCodeType.Method
    void removeByName(String name);
}
