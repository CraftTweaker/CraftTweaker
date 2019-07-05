package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.managers.CTRecipeManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;

public class ActionAddRecipe implements IRuntimeAction {
    
    private final IRecipeType recipeType;
    private final IRecipe recipe;
    private final String subType;
    
    public ActionAddRecipe(IRecipeType recipeType, IRecipe recipe, String subType) {
        this.recipeType = recipeType;
        this.recipe = recipe;
        this.subType = subType;
    }
    
    @Override
    public void apply() {
        CTRecipeManager.recipeManager.recipes.get(recipeType).put(recipe.getId(), recipe);
    }
    
    @Override
    public String describe() {
        return "Adding \"" + Registry.RECIPE_TYPE.getKey(recipeType) + "\" recipe, of type: \"" + subType + "\", with name: \"" + recipe.getId() + "\" that outputs: " + new MCItemStackMutable(recipe.getRecipeOutput());
    }
}
