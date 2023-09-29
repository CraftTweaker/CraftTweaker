package com.blamejared.crafttweaker.api.action.recipe.generic;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.regex.Pattern;

public class ActionRemoveGenericRecipeByRegex extends ActionRemoveGenericRecipeBase {
    
    private final Pattern pattern;
    
    public ActionRemoveGenericRecipeByRegex(String regex) {
        
        pattern = Pattern.compile(regex);
    }
    
    @Override
    public String describe() {
        
        return String.format("Removing all recipes that match the regex '%s'", pattern.pattern());
    }
    
    @Override
    protected boolean shouldRemove(RecipeHolder<?> holder) {
        
        final String id = holder.id().toString();
        return pattern.matcher(id).matches();
    }
    
}
