package com.blamejared.crafttweaker.api.action.recipe.generic;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.function.Predicate;

public class ActionRemoveGenericRecipeByModId extends ActionRemoveGenericRecipeBase {
    
    private final String modId;
    private final Predicate<String> exclude;
    
    public ActionRemoveGenericRecipeByModId(String modId, Predicate<String> exclude) {
        
        this.modId = modId;
        this.exclude = exclude;
    }
    
    @Override
    public String describe() {
        
        final String message = String.format("Removing all recipes from modId \"%s\"", modId);
        return exclude == null ? message : (message + ", while retaining recipes matching a filter");
    }
    
    @Override
    protected boolean shouldRemove(Recipe<?> recipe) {
        
        final ResourceLocation id = recipe.getId();
        if(!id.getNamespace().equals(modId)) {
            return false;
        }
        
        return exclude == null || !exclude.test(id.getPath());
    }
    
}
