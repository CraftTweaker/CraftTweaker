package com.blamejared.crafttweaker.impl.actions.recipes.generic;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ActionWholeRegistryBase implements IRuntimeAction {
    
    private RecipeManager getRecipeManager() {
        return CTCraftingTableManager.recipeManager;
    }
    
    protected Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> getRecipesByType() {
        final HashMap<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> result = new HashMap<>(getRecipeManager().recipes);
        result.remove(CraftTweaker.RECIPE_TYPE_SCRIPTS);
        return result;
    }
    
    protected String makeRecipeList(Map<String, Integer> recipeTypes) {
        return recipeTypes.entrySet().stream()
                .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
