package com.blamejared.crafttweaker.impl.actions.recipes.whole_registry;

import net.minecraft.item.crafting.IRecipe;

import java.util.regex.Pattern;

public class ActionRemoveFromWholeRegistryByRegex extends AbstractActionRemoveFromWholeRegistry {
    
    private final Pattern pattern;
    
    public ActionRemoveFromWholeRegistryByRegex(String regex) {
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
