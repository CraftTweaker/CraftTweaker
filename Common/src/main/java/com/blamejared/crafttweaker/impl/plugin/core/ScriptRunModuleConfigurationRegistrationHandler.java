package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IScriptRunModuleConfiguratorRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

final class ScriptRunModuleConfigurationRegistrationHandler implements IScriptRunModuleConfiguratorRegistrationHandler {
    
    private final Map<String, IScriptRunModuleConfigurator> configurators;
    
    private ScriptRunModuleConfigurationRegistrationHandler() {
        
        this.configurators = new HashMap<>();
    }
    
    static Stream<Map.Entry<String, IScriptRunModuleConfigurator>> gather(final Consumer<IScriptRunModuleConfiguratorRegistrationHandler> populatingConsumer) {
        
        final ScriptRunModuleConfigurationRegistrationHandler handler = new ScriptRunModuleConfigurationRegistrationHandler();
        populatingConsumer.accept(handler);
        return handler.configurators.entrySet().stream();
    }
    
    @Override
    public void registerConfigurator(final String loader, final IScriptRunModuleConfigurator configurator) {
        
        if(this.configurators.containsKey(loader)) {
            
            throw new IllegalArgumentException("Loader " + loader + " has a configurator registered already");
        }
        
        this.configurators.put(loader, configurator);
    }
    
}
