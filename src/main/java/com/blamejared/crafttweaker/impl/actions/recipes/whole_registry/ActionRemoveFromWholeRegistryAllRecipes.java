package com.blamejared.crafttweaker.impl.actions.recipes.whole_registry;

import java.util.Map;

public class ActionRemoveFromWholeRegistryAllRecipes extends AbstractActionWholeRegistry {
    
    @Override
    public void apply() {
        getRecipesByType().values().forEach(Map::clear);
    }
    
    @Override
    public String describe() {
        return "Removing all recipes";
    }
}
