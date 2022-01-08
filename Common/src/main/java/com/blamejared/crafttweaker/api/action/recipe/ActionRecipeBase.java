package com.blamejared.crafttweaker.api.action.recipe;


import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Map;

public abstract class ActionRecipeBase<T extends Recipe<?>> implements IRuntimeAction {
    
    private final IRecipeManager<T> manager;
    
    public ActionRecipeBase(IRecipeManager<T> manager) {
        
        this.manager = manager;
    }
    
    public IRecipeManager<T> getManager() {
        
        return manager;
    }
    
    public Map<ResourceLocation, T> getRecipes() {
        
        return this.getManager().getRecipes();
    }
    
    public RecipeType<T> getRecipeType() {
        
        return this.getManager().getRecipeType();
    }
    
    public ResourceLocation getRecipeTypeName() {
        
        return Services.REGISTRY.getRegistryKey(getRecipeType());
    }
    
    
}
