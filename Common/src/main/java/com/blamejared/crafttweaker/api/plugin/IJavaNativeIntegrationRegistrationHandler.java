package com.blamejared.crafttweaker.api.plugin;

public interface IJavaNativeIntegrationRegistrationHandler {
    
    void registerClassFor(final String loader, final Class<?> clazz);
    
}
