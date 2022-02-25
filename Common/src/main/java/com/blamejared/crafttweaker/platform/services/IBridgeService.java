package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunManager;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;

public interface IBridgeService {
    
    ICraftTweakerRegistry registry();
    
    IScriptRunManager scriptRunManager();
    
    IScriptRunModuleConfigurator defaultScriptRunModuleConfigurator(final String basePackage);
    
}
