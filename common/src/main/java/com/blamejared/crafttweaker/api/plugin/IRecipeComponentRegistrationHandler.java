package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;

/**
 * Manages the registration of {@link IRecipeComponent}s for CraftTweaker.
 *
 * <p>Refer to the recipe component documentation for more information.</p>
 *
 * @since 10.0.0
 */
public interface IRecipeComponentRegistrationHandler {
    
    /**
     * Registers the given {@link IRecipeComponent}.
     *
     * <p>It is disallowed for two recipe components to be registered under the same id.</p>
     *
     * @param component The component that needs to be registered.
     * @param <T>       The type of objects pointed to by this recipe component
     *
     * @throws IllegalArgumentException If a recipe component with the same ID already exists.
     * @since 10.0.0
     */
    <T> void registerRecipeComponent(final IRecipeComponent<T> component);
    
}
