package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.logging.ILoggerRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunManager;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.platform.helper.IAccessibleElementsProvider;

public interface IBridgeService {
    
    ICraftTweakerRegistry registry();
    
    IScriptRunManager scriptRunManager();
    
    IScriptRunModuleConfigurator defaultScriptRunModuleConfigurator(final String basePackage);
    
    IAccessibleElementsProvider accessibleElementsProvider();
    
    ILoggerRegistry loggerRegistry();
    
}
