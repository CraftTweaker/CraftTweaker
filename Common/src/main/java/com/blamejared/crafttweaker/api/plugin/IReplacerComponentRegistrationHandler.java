package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingFilter;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import net.minecraft.resources.ResourceLocation;

/**
 * Handles the registration of various components used specifically for recipe replacement.
 *
 * <p>The various integrations possible are global targeting filters, and targeting strategies. Refer to their specific
 * documentation for more details. Note that it is not necessary to register filtering rules.</p>
 *
 * @see com.blamejared.crafttweaker.api.recipe.replacement.Replacer
 * @since 10.0.0
 */
public interface IReplacerComponentRegistrationHandler {
    
    /**
     * Registers a {@link ITargetingFilter} that will be applied globally.
     *
     * <p>This essentially allows you to remove recipes from the list of recipes that a replacer is allowed to see or
     * perform additional changes. Note that this targeting filter is global and <strong>positive</strong>, meaning that
     * it will be executed on every replacer instance and will be responsible for determining which recipes the replacer
     * is allowed to inspect.</p>
     *
     * <p>If this filter should be conditional, refer to
     * {@link com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule} instead.</p>
     *
     * @param filter The global filter that needs to be registered.
     *
     * @since 10.0.0
     */
    void registerTargetingFilter(final ITargetingFilter filter);
    
    /**
     * Registers a {@link ITargetingStrategy} for script usage.
     *
     * <p>It is not allowed to register multiple targeting strategies with the same ID.</p>
     *
     * @param id       The ID of the targeting strategy.
     * @param strategy The targeting strategy that needs to be registered.
     *
     * @throws IllegalArgumentException If a strategy was already registered with the given id.
     * @since 10.0.0
     */
    void registerTargetingStrategy(final ResourceLocation id, final ITargetingStrategy strategy);
    
}
