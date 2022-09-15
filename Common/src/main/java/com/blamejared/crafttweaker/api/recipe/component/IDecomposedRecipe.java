package com.blamejared.crafttweaker.api.recipe.component;

import java.util.List;
import java.util.Set;

public interface IDecomposedRecipe {
    
    static DecomposedRecipeBuilder builder() {
        return DecomposedRecipeBuilder.of();
    }
    
    <C> List<C> get(final IRecipeComponent<C> component);
    
    <C> void set(final IRecipeComponent<C> component, final List<C> object);
    
    Set<IRecipeComponent<?>> components();
    
}
