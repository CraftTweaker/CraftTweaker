package com.blamejared.crafttweaker.api.action.recipe.generic;

import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.recipe.manager.RecipeManagerWrapper;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Map;
import java.util.TreeMap;

public abstract class ActionRemoveGenericRecipeBase extends ActionWholeRegistryBase {
    
    @Override
    public void apply() {
        
        final Map<String, Integer> numberOfRemovedRecipesByType = new TreeMap<>();
        int numberOfRemovedRecipes = 0;
        
        Map<RecipeType<Recipe<?>>, RecipeList<?>> recipeLists = getRecipeLists();
        for(RecipeType<Recipe<?>> recipeType : recipeLists.keySet()) {
            int removedRecipes = applyToRegistry(recipeLists.get(recipeType));
            if(removedRecipes > 0) {
                final String commandString = new RecipeManagerWrapper(recipeType).getCommandString();
                numberOfRemovedRecipesByType.put(commandString, removedRecipes);
                numberOfRemovedRecipes += removedRecipes;
            }
        }
        final int numberOfRecipeTypes = numberOfRemovedRecipesByType.size();
        final String recipeTypeList = makeRecipeList(numberOfRemovedRecipesByType);
        this.logger()
                .info("Removed {} recipes registered in these {} recipe managers: {}", numberOfRemovedRecipes, numberOfRecipeTypes, recipeTypeList);
    }
    
    private int applyToRegistry(RecipeList<?> list) {
        
        final int initialSize = list.getSize();
        list.removeByRecipeTest(this::shouldRemove);
        return initialSize - list.getSize();
    }
    
    protected abstract boolean shouldRemove(Recipe<?> recipe);
    
}
