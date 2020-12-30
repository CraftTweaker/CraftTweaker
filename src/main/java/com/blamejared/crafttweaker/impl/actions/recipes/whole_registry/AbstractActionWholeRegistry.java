package com.blamejared.crafttweaker.impl.actions.recipes.whole_registry;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractActionWholeRegistry implements IRuntimeAction {
    
    private RecipeManager getRecipeManager() {
        return CTCraftingTableManager.recipeManager;
    }
    
    protected Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> getRecipesByType() {
        final HashMap<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> result = new HashMap<>(getRecipeManager().recipes);
        result.remove(CraftTweaker.RECIPE_TYPE_SCRIPTS);
        return result;
    }
}
