package com.blamejared.crafttweaker.api.managers;

import net.minecraft.item.crafting.IRecipeType;

public class RecipeManagerWrapper implements IRecipeManager {
    
    private final IRecipeType recipeType;
    
    public RecipeManagerWrapper(IRecipeType recipeType) {
        this.recipeType = recipeType;
    }
    
    @Override
    public IRecipeType getRecipeType() {
        return recipeType;
    }
}
