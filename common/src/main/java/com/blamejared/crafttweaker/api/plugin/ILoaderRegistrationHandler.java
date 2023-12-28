package com.blamejared.crafttweaker.api.plugin;

/**
 * Manages the registration of {@link com.blamejared.crafttweaker.api.zencode.IScriptLoader}s for CraftTweaker.
 *
 * <p>Refer to the documentation for loaders for more information on what they represent.</p>
 *
 * @since 9.1.0
 */
public interface ILoaderRegistrationHandler {
    
    /**
     * Registers a loader with the given name and that inherits from the specified loaders.
     *
     * <p>Inheriting from a loader means that all classes that are available to that loader are also available to the
     * loader that is currently being registered.</p>
     *
     * <p>It is not necessary for a child loader to be registered after its parents, as long as both are registered in
     * the same time-frame.</p>
     *
     * @param name             The name of the loader that should be registered.
     * @param inheritedLoaders The names of other loaders this loader wants to inherit from.
     *
     * @since 9.1.0
     */
    void registerLoader(final String name, final String... inheritedLoaders);
    
}
