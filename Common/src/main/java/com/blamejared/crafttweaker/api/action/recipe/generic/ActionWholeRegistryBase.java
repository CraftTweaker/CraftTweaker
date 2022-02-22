package com.blamejared.crafttweaker.api.action.recipe.generic;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ActionWholeRegistryBase implements IRuntimeAction {
    
    private RecipeManager getRecipeManager() {
        
        return CraftTweakerAPI.getRecipeManager();
    }
    
    protected Map<RecipeType<?>, RecipeList<?>> getRecipeLists() {
    
        return RecipeTypeBracketHandler.getManagerInstances().stream().map(IRecipeManager::getRecipeList).collect(Collectors.toMap(RecipeList::getRecipeType, recipeRecipeList -> recipeRecipeList));
    }
    
    protected Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> getRecipesByType() {
        
        final HashMap<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> result = new HashMap<>(CraftTweakerAPI.getAccessibleRecipeManager()
                .getRecipes());
        result.remove(CraftTweakerRegistries.RECIPE_TYPE_SCRIPTS);
        return result;
    }
    
    protected String makeRecipeList(Map<String, Integer> recipeTypes) {
        
        return recipeTypes.entrySet().stream()
                .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", ", "[", "]"));
    }
    
}
