package com.blamejared.crafttweaker.api.action.recipe.generic;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.RecipeList;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.impl.script.ScriptRecipeType;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ActionWholeRegistryBase extends CraftTweakerAction implements IRuntimeAction {
    
    private RecipeManager getRecipeManager() {
        
        return CraftTweakerAPI.getAccessibleElementsProvider().recipeManager();
    }
    
    protected Map<RecipeType<Recipe<?>>, RecipeList<?>> getRecipeLists() {
        
        return RecipeTypeBracketHandler.getManagerInstances()
                .stream()
                .map(IRecipeManager::getRecipeList)
                .collect(Collectors.toMap(RecipeList::getRecipeType, Function.identity()));
    }
    
    protected Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> getRecipesByType() {
        
        final AccessRecipeManager manager = CraftTweakerAPI.getAccessibleElementsProvider().accessibleRecipeManager();
        final HashMap<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> result = new HashMap<>(manager.crafttweaker$getRecipes());
        result.remove(ScriptRecipeType.INSTANCE);
        return result;
    }
    
    protected String makeRecipeList(Map<String, Integer> recipeTypes) {
        
        return recipeTypes.entrySet().stream()
                .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", ", "[", "]"));
    }
    
}
