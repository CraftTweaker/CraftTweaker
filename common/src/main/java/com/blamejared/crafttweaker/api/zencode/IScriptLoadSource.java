package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.resources.ResourceLocation;

/**
 * Identifies a script load source.
 *
 * <p>A script load source is uniquely identified by its id, represented by a {@link ResourceLocation}. A load source is
 * used to identify who or what is responsible for the loading of scripts and which circumstances have lead to the
 * loading.</p>
 *
 * <p>Each script load source must be registered through a
 * {@link com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin} before being able to be used.</p>
 *
 * <p>This is a {@linkplain FunctionalInterface functional interface} whose functional method is {@link #id()}.</p>
 *
 * @since 9.1.0
 */
@FunctionalInterface
public interface IScriptLoadSource {
    
    /**
     * Attempts to find a load source with the given ID in the registry.
     *
     * <p>It is not allowed to call this method before registries have been properly initialized. If such an action is
     * performed, then the result is undefined behavior.</p>
     *
     * @param id The id of the load source to find.
     *
     * @return The load source with the given id.
     *
     * @throws IllegalArgumentException If no such load source was registered.
     * @since 9.1.0
     */
    static IScriptLoadSource find(final ResourceLocation id) {
        
        return CraftTweakerAPI.getRegistry().findLoadSource(id);
    }
    
    /**
     * The ID of the load source.
     *
     * @return The ID of the load source.
     *
     * @since 9.1.0
     */
    ResourceLocation id();
    
}
