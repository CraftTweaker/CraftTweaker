package com.blamejared.crafttweaker.api.action.recipe.generic;

import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.recipe.manager.RecipeManagerWrapper;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Map;
import java.util.TreeMap;

public class ActionRemoveAllGenericRecipes extends ActionWholeRegistryBase {
    
    @Override
    public void apply() {
        
        Map<RecipeType<Recipe<?>>, RecipeList<?>> recipeLists = getRecipeLists();
        
        final Map<String, Integer> numberOfRemovedRecipesByType = new TreeMap<>();
        int totalRemoved = 0;
        
        for(RecipeType<Recipe<?>> recipeType : recipeLists.keySet()) {
            final int removedRecipes = remove(recipeLists.get(recipeType));
            if(removedRecipes > 0) {
                totalRemoved += removedRecipes;
                final String commandString = new RecipeManagerWrapper(recipeType).getCommandString();
                numberOfRemovedRecipesByType.put(commandString, removedRecipes);
            }
        }
        
        final int managerCount = numberOfRemovedRecipesByType.size();
        final String recipeTypeList = makeRecipeList(numberOfRemovedRecipesByType);
        this.logger()
                .info("Removed {} recipes across these {} managers: {}", totalRemoved, managerCount, recipeTypeList);
    }
    
    private int remove(RecipeList<?> list) {
        
        final int size = list.getSize();
        list.removeAll();
        return size;
    }
    
    @Override
    public String describe() {
        
        return "Removing all recipes";
    }
    
}
