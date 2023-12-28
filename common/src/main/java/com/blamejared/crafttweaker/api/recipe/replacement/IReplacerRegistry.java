package com.blamejared.crafttweaker.api.recipe.replacement;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

/**
 * Holds all registries related specifically to {@link Replacer}s.
 *
 * <p>An instance of this class can be obtained with {@link com.blamejared.crafttweaker.api.ICraftTweakerRegistry}.</p>
 *
 * @since 10.0.0
 */
public interface IReplacerRegistry {
    
    /**
     * Gets a {@link Collection} of all global {@linkplain ITargetingFilter filters} registered.
     *
     * @return A collection of global filters.
     *
     * @since 10.0.0
     */
    Collection<ITargetingFilter> filters();
    
    /**
     * Finds the {@link ITargetingStrategy} with the given id, if available.
     *
     * <p>It is illegal to invoke this method before this registry has been built: doing so will result in undefined
     * behavior.</p>
     *
     * @param id The id uniquely identifying the targeting strategy.
     *
     * @return The targeting strategy with the given id.
     *
     * @throws NullPointerException If no strategy with the given id has been registered.
     * @since 10.0.0
     */
    ITargetingStrategy findStrategy(final ResourceLocation id);
    
    /**
     * Gets a {@link Collection} with all ids used to identify known {@link ITargetingStrategy}.
     *
     * @return A collection of names.
     *
     * @since 10.0.0
     */
    Collection<ResourceLocation> allStrategyNames();
    
}
