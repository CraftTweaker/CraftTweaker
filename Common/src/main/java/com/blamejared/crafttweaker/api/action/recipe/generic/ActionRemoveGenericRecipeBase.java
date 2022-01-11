package com.blamejared.crafttweaker.api.action.recipe.generic;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.recipe.manager.RecipeManagerWrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Map;
import java.util.TreeMap;

public abstract class ActionRemoveGenericRecipeBase extends ActionWholeRegistryBase {
    
    @Override
    public void apply() {
        
        final Map<String, Integer> numberOfRemovedRecipesByType = new TreeMap<>();
        int numberOfRemovedRecipes = 0;
        
        final Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipesByType = getRecipesByType();
        for(RecipeType recipeType : recipesByType.keySet()) {
            int removedRecipes = applyToRegistry(recipesByType.get(recipeType));
            if(removedRecipes > 0) {
                final String commandString = new RecipeManagerWrapper(recipeType).getCommandString();
                numberOfRemovedRecipesByType.put(commandString, removedRecipes);
                numberOfRemovedRecipes += removedRecipes;
            }
        }
        final int numberOfRecipeTypes = numberOfRemovedRecipesByType.size();
        final String recipeTypeList = makeRecipeList(numberOfRemovedRecipesByType);
        CraftTweakerAPI.LOGGER.info("Removed {} recipes registered in these {} recipe managers: {}", numberOfRemovedRecipes, numberOfRecipeTypes, recipeTypeList);
    }
    
    private int applyToRegistry(Map<ResourceLocation, Recipe<?>> registry) {
        
        final int initialSize = registry.size();
        registry.values().removeIf(this::shouldRemove);
        return initialSize - registry.size();
    }
    
    protected abstract boolean shouldRemove(Recipe<?> recipe);
    
}
