package com.blamejared.crafttweaker.impl.actions.recipes.generic;

import net.minecraft.item.crafting.IRecipe;

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
    protected boolean shouldRemove(IRecipe<?> recipe) {
        
        final String id = recipe.getId().toString();
        return pattern.matcher(id).matches();
    }
    
}
