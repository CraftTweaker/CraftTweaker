package com.blamejared.crafttweaker.api.plugin;

public interface ILoaderRegistrationHandler {
    
    void registerLoader(final String name, final String... inheritedLoaders);
    
}
