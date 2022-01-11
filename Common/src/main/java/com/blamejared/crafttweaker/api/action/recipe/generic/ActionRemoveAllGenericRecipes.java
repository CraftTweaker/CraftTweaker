package com.blamejared.crafttweaker.api.action.recipe.generic;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.recipe.manager.RecipeManagerWrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Map;
import java.util.TreeMap;

public class ActionRemoveAllGenericRecipes extends ActionWholeRegistryBase {
    
    @Override
    public void apply() {
        
        final Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipesByType = getRecipesByType();
        final Map<String, Integer> numberOfRemovedRecipesByType = new TreeMap<>();
        int totalRemoved = 0;
        
        for(RecipeType recipeType : recipesByType.keySet()) {
            final int removedRecipes = remove(recipesByType.get(recipeType));
            if(removedRecipes > 0) {
                totalRemoved += removedRecipes;
                final String commandString = new RecipeManagerWrapper(recipeType).getCommandString();
                numberOfRemovedRecipesByType.put(commandString, removedRecipes);
            }
        }
        
        final int managerCount = numberOfRemovedRecipesByType.size();
        final String recipeTypeList = makeRecipeList(numberOfRemovedRecipesByType);
        CraftTweakerAPI.LOGGER.info("Removed {} recipes across these {} managers: {}", totalRemoved, managerCount, recipeTypeList);
    }
    
    private int remove(Map<ResourceLocation, Recipe<?>> map) {
        
        final int size = map.size();
        map.clear();
        return size;
    }
    
    @Override
    public String describe() {
        
        return "Removing all recipes";
    }
    
}
