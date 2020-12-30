package com.blamejared.crafttweaker.impl.actions.recipes.whole_registry;

import com.blamejared.crafttweaker.api.logger.ILogger;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class ActionRemoveFromWholeRegistryByName extends AbstractActionRemoveFromWholeRegistry {
    
    private final String name;
    
    public ActionRemoveFromWholeRegistryByName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(ResourceLocation.isResouceNameValid(name)) {
            return true;
        }
        
        logger.warning(String.format("Invalid recipe name: \"%s\"", name));
        return false;
    }
    
    @Override
    public String describe() {
        return String.format("Removing all recipes with name \"%s\"", name);
    }
    
    @Override
    protected boolean shouldRemove(IRecipe<?> recipe) {
        final String recipeId = recipe.getId().toString();
        return name.equals(recipeId);
    }
}
