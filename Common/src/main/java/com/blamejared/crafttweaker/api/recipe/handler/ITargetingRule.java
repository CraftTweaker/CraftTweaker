package com.blamejared.crafttweaker.api.recipe.handler;


import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.world.item.crafting.Recipe;

/**
 * Represents a rule used to identify which recipes should have their ingredients replaced by {@link IReplacementRule}s.
 *
 * <p>Each targeting rule should be considered pure, which means it should not rely on external and/or global state to
 * decide whether a recipe may be replaced or not. A targeting rule may in fact be queried multiple times for the same
 * recipe.</p>
 *
 * @since 9.1.0
 */
public interface ITargetingRule {
    
    /**
     * Indicates whether the given recipe should undergo ingredient replacement or not.
     *
     * <p>Rules are allowed to check any aspect of the provided objects to validate the recipe, as long as the result
     * remains consistent across multiple calls with the same objects.</p>
     *
     * @param recipe  The recipe to check for targeting.
     * @param manager The recipe manager that is responsible for the given recipe.
     *
     * @return Whether the given recipe should undergo ingredient replacement or not.
     *
     * @since 9.1.0
     */
    boolean shouldBeReplaced(final Recipe<?> recipe, final IRecipeManager<?> manager);
    
    /**
     * Describes in a short and simple sentence the behavior of this rule.
     *
     * @return The description of this rule.
     *
     * @apiNote This string will be used in user interactions and log output. For this reason, it should be descriptive
     * yet concise at the same time.
     * @since 9.1.0
     */
    String describe();
    
}
