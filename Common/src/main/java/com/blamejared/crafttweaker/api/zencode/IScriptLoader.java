package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;

import java.util.Collection;

/**
 * Identifies a script loader.
 *
 * <p>A script loader allows to define a set of scripts that should be loaded at different points in time, allowing thus
 * for different classes or functionality to be exposed to them.</p>
 *
 * <p>Each loader is identified by a name. It is customary, although not required, for the name to be a single all
 * lower-cased word. A loader can also inherit classes from other loaders, allowing for inheritance chains to be
 * created. All loaders <strong>automatically</strong> inherit from the global loader: a fake loader used to represent
 * classes that should be available everywhere regardless of configuration.</p>
 *
 * <p>Each script loader needs to be registered through a
 * {@link com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin} before it can be used.</p>
 *
 * @since 9.1.0
 */
public interface IScriptLoader {
    
    /**
     * Attempts to find a loader with the given name in the registry.
     *
     * <p>It is not allowed to call this method before registries have been properly initialized. If such an action is
     * performed, then the result is undefined behavior. It is also not allowed to query the global loader.</p>
     *
     * @param name The name of the loader to find.
     *
     * @return The loader with the given name.
     *
     * @throws IllegalArgumentException If no such loader was registered or if the name identifies the global loader.
     * @since 9.1.0
     */
    static IScriptLoader find(final String name) {
        
        return CraftTweakerAPI.getRegistry().findLoader(name);
    }
    
    /**
     * Gets the name of the loader.
     *
     * @return The name of the loader.
     *
     * @since 9.1.0
     */
    String name();
    
    /**
     * Gets a {@link Collection} of all loaders from which this loader inherits from.
     *
     * @return All parents of this loader.
     *
     * @since 9.1.0
     */
    Collection<IScriptLoader> inheritedLoaders();
    
}
