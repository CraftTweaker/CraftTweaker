package com.blamejared.crafttweaker.api.recipe.replacement.rule;

import com.blamejared.crafttweaker.api.recipe.handler.ITargetingRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

public final class SpecificRecipesTargetingRule implements ITargetingRule {
    
    private final Collection<Recipe<?>> recipes;
    
    private SpecificRecipesTargetingRule(final Collection<Recipe<?>> recipes) {
        
        this.recipes = Collections.unmodifiableCollection(recipes);
    }
    
    public static SpecificRecipesTargetingRule of(final Collection<Recipe<?>> recipes) {
        
        if(recipes.isEmpty()) {
            throw new IllegalArgumentException("Unable to create a specific recipes targeting rule without any targets");
        }
        return new SpecificRecipesTargetingRule(recipes);
    }
    
    public static SpecificRecipesTargetingRule of(final Recipe<?>... recipes) {
        
        return of(new HashSet<>(Arrays.asList(recipes)));
    }
    
    @Override
    public boolean shouldBeReplaced(final Recipe<?> recipe, final IRecipeManager<?> manager) {
        
        return this.recipes.stream().anyMatch(it -> it.equals(recipe));
    }
    
    @Override
    public String describe() {
        
        return this.recipes.stream()
                .map(Recipe::getId)
                .map(ResourceLocation::toString)
                .collect(Collectors.joining(", ", "recipes {", "}"));
    }
    
    public Collection<Recipe<?>> recipes() {
        
        return this.recipes;
    }
    
}
