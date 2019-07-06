package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.impl.managers.CTRecipeManager;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ActionRemoveRecipeByRegex implements IRuntimeAction {
    
    private final IRecipeType recipeType;
    private final Pattern compiledPat;
    
    public ActionRemoveRecipeByRegex(IRecipeType recipeType, String regex) {
        this.recipeType = recipeType;
        this.compiledPat = Pattern.compile(regex);
    }
    
    @Override
    public void apply() {
        CTRecipeManager.recipeManager.recipes.get(recipeType).keySet().stream().filter(resourceLocation -> compiledPat.matcher(resourceLocation.toString()).matches()).collect(Collectors.toList()).forEach(CTRecipeManager.recipeManager.recipes.get(recipeType)::remove);
    }
    
    @Override
    public String describe() {
        return "Removing \"" + Registry.RECIPE_TYPE.getKey(recipeType) + "\" recipe with names that match the regex: \"" + compiledPat.pattern() + "\"";
    }
    
}
