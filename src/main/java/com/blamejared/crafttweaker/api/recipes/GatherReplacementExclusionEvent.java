package com.blamejared.crafttweaker.api.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Fired whenever replacement exclusions for a specific {@link IRecipeManager} need to be gathered.
 *
 * <p>Recipe names that are added in this event will then be excluded from the set of recipes that {@code Replacer}s are
 * allowed to modify, meaning that users will have to remove and add recipes manually instead of being able to resort to
 * automated tools.</p>
 *
 * <p>Mods should add recipes to this event <strong>if and only if</strong> replacement is actually impossible, and not
 * based on whether replacement is supported on their end or not. As an example, {@code minecraft:armor_dye} is a recipe
 * that is hardcoded and thus cannot have its ingredients replaced: this is a good candidate to addition.</p>
 */
public final class GatherReplacementExclusionEvent extends Event {
    private final IRecipeManager targetedManager;
    private final Set<ResourceLocation> excludedRecipes;
    
    /**
     * Creates a new event instance.
     *
     * @param manager The manager this event is being fired for.
     */
    public GatherReplacementExclusionEvent(final IRecipeManager manager) {
        this.targetedManager = manager;
        this.excludedRecipes = new HashSet<>();
    }
    
    /**
     * Gets the targeted manager this event is being fired for.
     *
     * @return The targeted manager this event is being fired for.
     */
    public IRecipeManager getTargetedManager() {
        return this.targetedManager;
    }
    
    /**
     * Gets an immutable view of the recipe names that have currently been excluded.
     *
     * @return An immutable view of the recipe names that have currently been excluded.
     */
    public Collection<ResourceLocation> getExcludedRecipes() {
        return Collections.unmodifiableCollection(this.excludedRecipes);
    }
    
    /**
     * Adds the given {@link ResourceLocation} to the list of recipe names that will be excluded.
     *
     * @param id The ID of the recipe to exclude.
     */
    public void addExclusion(final ResourceLocation id) {
        this.excludedRecipes.add(id);
    }
    
    /**
     * Adds the name of the given {@link IRecipe} to the list of recipe names that will be excluded.
     *
     * @implNote The effect of this method is the same as if a call like {@code event.addExclusion(recipe.getId())}
     * would have been made.
     *
     * @param recipe The recipe that will be excluded.
     */
    public void addExclusion(final IRecipe<?> recipe) {
        this.addExclusion(recipe.getId());
    }
}
