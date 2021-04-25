package com.blamejared.crafttweaker.impl.actions.recipes.generic;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.managers.RecipeManagerWrapper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class ActionRemoveGenericRecipeBase extends ActionWholeRegistryBase {
    
    @Override
    public void apply() {
        final Map<String, Integer> numberOfRemovedRecipesByType = new TreeMap<>();
        int numberOfRemovedRecipes = 0;
        
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesByType = getRecipesByType();
        for(IRecipeType<?> recipeType : recipesByType.keySet()) {
            int removedRecipes = applyToRegistry(recipesByType.get(recipeType));
            if(removedRecipes > 0) {
                final String commandString = new RecipeManagerWrapper(recipeType).getCommandString();
                numberOfRemovedRecipesByType.put(commandString, removedRecipes);
                numberOfRemovedRecipes += removedRecipes;
            }
        }
        final int numberOfRecipeTypes = numberOfRemovedRecipesByType.size();
        final String recipeTypeList = makeRecipeList(numberOfRemovedRecipesByType);
        CraftTweakerAPI.logInfo("Removed %s recipes registered in these %s recipe managers: %s", numberOfRemovedRecipes, numberOfRecipeTypes, recipeTypeList);
    }
    
    private int applyToRegistry(Map<ResourceLocation, IRecipe<?>> registry) {
    
        final int initialSize = registry.size();
        registry.values().removeIf(this::shouldRemove);
        return initialSize - registry.size();
    }
    
    protected abstract boolean shouldRemove(IRecipe<?> recipe);
}
