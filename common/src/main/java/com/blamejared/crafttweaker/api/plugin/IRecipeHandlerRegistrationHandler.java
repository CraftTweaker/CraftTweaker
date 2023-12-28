package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import net.minecraft.world.item.crafting.Recipe;

/**
 * Manages the registration of {@link IRecipeHandler}s for CraftTweaker.
 *
 * <p>Refer to the recipe handler documentation for more information.</p>
 *
 * @since 9.1.0
 */
public interface IRecipeHandlerRegistrationHandler {
    
    /**
     * Registers a recipe handler for the given recipe class.
     *
     * <p>It is not allowed to register multiple handlers for the same recipe class or register a generic handler for
     * the {@link Recipe} class directly. Having multiple recipe classes bound to the same handler is on the other
     * hand allowed.</p>
     *
     * @param recipe  The class of the recipe for which the handler should be registered.
     * @param handler The handler instance that needs to be registered.
     * @param <T>     The type of the recipe for which the handler is.
     *
     * @since 9.1.0
     */
    <T extends Recipe<?>> void registerRecipeHandler(final Class<? extends T> recipe, final IRecipeHandler<T> handler);
    
}
