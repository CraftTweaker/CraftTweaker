package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;

import java.util.Collection;

public interface IScriptLoader {
    
    static IScriptLoader find(final String name) {
        
        return CraftTweakerAPI.getRegistry().findLoader(name);
    }
    
    String name();
    
    Collection<IScriptLoader> inheritedLoaders();
    
}
