package com.blamejared.crafttweaker.api.action.recipe.generic;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.logging.log4j.Logger;

public class ActionRemoveGenericRecipeByName extends ActionRemoveGenericRecipeBase {
    
    private final String name;
    
    public ActionRemoveGenericRecipeByName(String name) {
        
        this.name = name;
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        if(ResourceLocation.tryParse(name) != null) {
            return true;
        }
        
        logger.warn("Invalid recipe name: \"{}\"", name);
        return false;
    }
    
    @Override
    public String describe() {
        
        return String.format("Removing all recipes with name \"%s\"", name);
    }
    
    @Override
    protected boolean shouldRemove(Recipe<?> recipe) {
        
        final String recipeId = recipe.getId().toString();
        return name.equals(recipeId);
    }
    
}
