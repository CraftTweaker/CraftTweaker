package com.blamejared.crafttweaker.api.plugin;

import net.minecraft.resources.ResourceLocation;

/**
 * Manages the registration of {@link com.blamejared.crafttweaker.api.zencode.IScriptLoadSource}s for CraftTweaker.
 *
 * <p>Refer to the documentation for load sources for more information related on what they represent and their
 * usage.</p>
 *
 * @since 9.1.0
 */
public interface IScriptLoadSourceRegistrationHandler {
    
    /**
     * Registers a load source with the given identifier.
     *
     * <p>Each load source must be registered with a unique identifier.</p>
     *
     * @param id The identifier of the load source.
     *
     * @since 9.1.0
     */
    void registerLoadSource(final ResourceLocation id);
    
}
