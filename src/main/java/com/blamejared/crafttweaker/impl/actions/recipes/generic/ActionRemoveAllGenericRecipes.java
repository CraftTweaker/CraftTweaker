package com.blamejared.crafttweaker.impl.actions.recipes.generic;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.managers.RecipeManagerWrapper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.TreeMap;

public class ActionRemoveAllGenericRecipes extends ActionWholeRegistryBase {
    
    @Override
    public void apply() {
        
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesByType = getRecipesByType();
        final Map<String, Integer> numberOfRemovedRecipesByType = new TreeMap<>();
        int totalRemoved = 0;
        
        for(IRecipeType<?> recipeType : recipesByType.keySet()) {
            final int removedRecipes = remove(recipesByType.get(recipeType));
            if(removedRecipes > 0) {
                totalRemoved += removedRecipes;
                final String commandString = new RecipeManagerWrapper(recipeType).getCommandString();
                numberOfRemovedRecipesByType.put(commandString, removedRecipes);
            }
        }
        
        final int managerCount = numberOfRemovedRecipesByType.size();
        final String recipeTypeList = makeRecipeList(numberOfRemovedRecipesByType);
        CraftTweakerAPI.logInfo("Removed %s recipes across these %s managers: %s", totalRemoved, managerCount, recipeTypeList);
    }
    
    private int remove(Map<ResourceLocation, IRecipe<?>> map) {
        
        final int size = map.size();
        map.clear();
        return size;
    }
    
    @Override
    public String describe() {
        
        return "Removing all recipes";
    }
    
}
