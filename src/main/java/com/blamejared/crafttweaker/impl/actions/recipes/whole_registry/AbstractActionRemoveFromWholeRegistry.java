package com.blamejared.crafttweaker.impl.actions.recipes.whole_registry;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.managers.RecipeManagerWrapper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class AbstractActionRemoveFromWholeRegistry extends AbstractActionWholeRegistry {
    
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
        final Set<Map.Entry<ResourceLocation, IRecipe<?>>> entries = registry.entrySet();
        final Iterator<Map.Entry<ResourceLocation, IRecipe<?>>> iterator = entries.iterator();
        
        int numberOfRecipesRemoved = 0;
        while(iterator.hasNext()) {
            final IRecipe<?> recipe = iterator.next().getValue();
            if(shouldRemove(recipe)) {
                iterator.remove();
                numberOfRecipesRemoved++;
            }
        }
        
        return numberOfRecipesRemoved;
    }
    
    protected abstract boolean shouldRemove(IRecipe<?> recipe);
}
