package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.openzen.zencode.java.ZenCodeType;

import java.util.stream.Stream;

/**
 * Represents a filter applied to the recipe list.
 *
 * <p>This filter is applied to {@link Replacer} instances and allows for the removal of certain set of recipes from
 * being able to be inspected by a replacer, or for additional global executions to be performed. Note that the filter
 * is <strong>positive</strong>, meaning that the filter determines which recipes are allowed to be examined by a
 * replacer, not the opposite.</p>
 *
 * <p>Script writers should refer to {@link IFilteringRule} instead.</p>
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is {@link #castFilter(Stream)}.</p>
 *
 * @since 10.0.0
 */
@Document("vanilla/api/recipe/replacement/ITargetingFilter")
//@FunctionalInterface
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.ITargetingFilter")
@ZenRegister
public interface ITargetingFilter {
    
    /**
     * Casts the filter onto the given {@link Stream} of {@link Recipe}s.
     *
     * <p>The filter can perform any operation required, which range from filtering, to simple mapping, to peeking as
     * needed.</p>
     *
     * @param allRecipes The {@link Stream} that needs to be filtered.
     *
     * @return The mutated stream.
     *
     * @since 10.0.0
     */
    Stream<RecipeHolder<?>> castFilter(final Stream<RecipeHolder<?>> allRecipes);
    
}
