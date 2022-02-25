package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;

public interface IScriptRunModuleConfiguratorRegistrationHandler {
    
    void registerConfigurator(final String loader, final IScriptRunModuleConfigurator configurator);
    
}
