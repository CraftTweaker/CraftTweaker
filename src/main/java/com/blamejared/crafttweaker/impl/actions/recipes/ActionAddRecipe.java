package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.registry.Registry;

public class ActionAddRecipe extends ActionRecipeBase {
    
    private final IRecipe recipe;
    private final String subType;
    
    public ActionAddRecipe(IRecipeManager recipeManager, IRecipe recipe, String subType) {
        super(recipeManager);
        this.recipe = recipe;
        this.subType = subType;
    }
    
    @Override
    public void apply() {
        getManager().getRecipes().put(recipe.getId(), recipe);
    }
    
    @Override
    public String describe() {
        return "Adding \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" recipe" + getSubTypeDescription() + ", with name: \"" + recipe.getId() + "\" that outputs: " + new MCItemStackMutable(recipe.getRecipeOutput());
    }
    
    private String getSubTypeDescription() {
        if(subType != null && !subType.trim().isEmpty()) {
            return ", of type: \"" + subType + "\"";
        } else {
            return "";
        }
    }
}
