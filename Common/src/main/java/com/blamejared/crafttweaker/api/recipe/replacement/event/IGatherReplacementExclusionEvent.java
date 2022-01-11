package com.blamejared.crafttweaker.api.recipe.replacement.event;


import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Collection;

/**
 * Fired whenever replacement exclusions for a specific {@link IRecipeManager} need to be gathered.
 *
 * <p>Recipe names that are added in this event will then be excluded from the set of recipes that {@code Replacer}s are
 * allowed to modify, meaning that users will have to remove and add recipes manually instead of being able to resort to
 * automated tools.</p>
 *
 * <p>Mods should add recipes to this event <strong>if and only if</strong> replacement is actually impossible, and not
 * based on whether replacement is supported on their end or not. As an example, {@code minecraft:armor_dye} is a recipe
 * that is hardcoded and thus cannot have its ingredients replaced: this is a good candidate to exclude.</p>
 *
 * @implNote By default, CraftTweaker automatically excludes all {@linkplain Recipe#isSpecial() special} recipes across
 * all <strong>vanilla</strong> recipe types. You don't need to add these to the exclusion list yourself.
 */
public interface IGatherReplacementExclusionEvent {
    
    /**
     * Gets the targeted manager this event is being fired for.
     *
     * @return The targeted manager this event is being fired for.
     */
    IRecipeManager getTargetedManager();
    
    /**
     * Gets an immutable view of the recipe names that have currently been excluded.
     *
     * @return An immutable view of the recipe names that have currently been excluded.
     */
    Collection<ResourceLocation> getExcludedRecipes();
    
    /**
     * Adds the given {@link ResourceLocation} to the list of recipe names that will be excluded.
     *
     * @param id The ID of the recipe to exclude.
     */
    void addExclusion(final ResourceLocation id);
    
    /**
     * Adds the name of the given {@link Recipe} to the list of recipe names that will be excluded.
     *
     * @param recipe The recipe that will be excluded.
     *
     * @implNote The effect of this method is the same as if a call like {@code event.addExclusion(recipe.getId())}
     * would have been made.
     */
    void addExclusion(final Recipe<?> recipe);
    
}
