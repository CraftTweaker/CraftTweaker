package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;

public interface IRecipeComponentRegistrationHandler {
    
    <T> void registerRecipeComponent(final IRecipeComponent<T> component);
    
}
