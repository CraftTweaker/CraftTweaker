package com.blamejared.crafttweaker.impl.managers;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.item.crafting.IRecipeType;

public class RecipeManagerWrapper implements IRecipeManager {
    
    private final IRecipeType<?> recipeType;
    
    public RecipeManagerWrapper(IRecipeType<?> recipeType) {
        
        this.recipeType = recipeType;
    }
    
    public static RecipeManagerWrapper makeOrNull(final IRecipeType<?> recipeType) {
        
        return recipeType == null ? null : new RecipeManagerWrapper(recipeType);
    }
    
    @Override
    public IRecipeType<?> getRecipeType() {
        
        return recipeType;
    }
    
    @Override
    public boolean equals(Object o) {
    
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        RecipeManagerWrapper that = (RecipeManagerWrapper) o;
        
        return recipeType.equals(that.recipeType);
    }
    
    @Override
    public int hashCode() {
        
        return recipeType.hashCode();
    }
    
}
