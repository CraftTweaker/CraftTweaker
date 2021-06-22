package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.ITargetingRule;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public final class SpecificRecipesTargetingRule implements ITargetingRule {
    private final Collection<? extends IRecipe<?>> recipes;
    
    private SpecificRecipesTargetingRule(final Collection<? extends IRecipe<?>> recipes) {
        this.recipes = Collections.unmodifiableCollection(recipes);
    }
    
    public static SpecificRecipesTargetingRule of(final Collection<? extends IRecipe<?>> recipes) {
        if (recipes.isEmpty()) {
            throw new IllegalArgumentException("Unable to create a specific recipes targeting rule without any targets");
        }
        return new SpecificRecipesTargetingRule(recipes);
    }
    
    public static SpecificRecipesTargetingRule of(final WrapperRecipe... recipes) {
        return of(Arrays.stream(recipes).map(WrapperRecipe::getRecipe).collect(Collectors.toSet()));
    }

    @Override
    public boolean shouldBeReplaced(final IRecipe<?> recipe, final IRecipeManager manager) {
        return this.recipes.contains(recipe);
    }
    
    @Override
    public String describe() {
        return this.recipes.stream()
                .map(IRecipe::getId)
                .map(ResourceLocation::toString)
                .collect(Collectors.joining(", ", "recipes {", "}"));
    }
    
}
