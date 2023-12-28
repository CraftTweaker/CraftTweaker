package com.blamejared.crafttweaker.impl.service;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.logging.ILoggerRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunManager;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker.impl.logging.LoggerRegistry;
import com.blamejared.crafttweaker.impl.registry.CraftTweakerRegistry;
import com.blamejared.crafttweaker.impl.script.scriptrun.DefaultScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.impl.script.scriptrun.ScriptRunManager;
import com.blamejared.crafttweaker.platform.helper.IAccessibleElementsProvider;
import com.blamejared.crafttweaker.platform.services.IBridgeService;

public final class BridgeService implements IBridgeService {
    
    @Override
    public ICraftTweakerRegistry registry() {
        
        return CraftTweakerRegistry.get();
    }
    
    @Override
    public IScriptRunManager scriptRunManager() {
        
        return ScriptRunManager.get();
    }
    
    @Override
    public IScriptRunModuleConfigurator defaultScriptRunModuleConfigurator(final String basePackage) {
        
        return DefaultScriptRunModuleConfigurator.of(basePackage);
    }
    
    @Override
    public IAccessibleElementsProvider accessibleElementsProvider() {
        
        return AccessibleElementsProvider.get();
    }
    
    @Override
    public ILoggerRegistry loggerRegistry() {
        
        return LoggerRegistry.get();
    }
    
}
