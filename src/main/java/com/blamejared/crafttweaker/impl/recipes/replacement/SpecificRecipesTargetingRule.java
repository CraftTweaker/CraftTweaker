package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.ITargetingRule;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

public final class SpecificRecipesTargetingRule implements ITargetingRule {
    private final Collection<WrapperRecipe> recipes;
    
    private SpecificRecipesTargetingRule(final Collection<WrapperRecipe> recipes) {
        this.recipes = Collections.unmodifiableCollection(recipes);
    }
    
    public static SpecificRecipesTargetingRule of(final Collection<WrapperRecipe> recipes) {
        if (recipes.isEmpty()) {
            throw new IllegalArgumentException("Unable to create a specific recipes targeting rule without any targets");
        }
        return new SpecificRecipesTargetingRule(recipes);
    }
    
    public static SpecificRecipesTargetingRule of(final WrapperRecipe... recipes) {
        return of(new HashSet<>(Arrays.asList(recipes)));
    }

    @Override
    public boolean shouldBeReplaced(final IRecipe<?> recipe, final IRecipeManager manager) {
        return this.recipes.stream().map(WrapperRecipe::getRecipe).anyMatch(it -> it.equals(recipe));
    }
    
    @Override
    public String describe() {
        return this.recipes.stream()
                .map(WrapperRecipe::getRecipe)
                .map(IRecipe::getId)
                .map(ResourceLocation::toString)
                .collect(Collectors.joining(", ", "recipes {", "}"));
    }
    
    public Collection<WrapperRecipe> recipes() {
        return this.recipes;
    }
}
