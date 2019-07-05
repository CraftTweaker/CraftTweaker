package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.managers.CTRecipeManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.stream.Collectors;

public class ActionRemoveOutputRecipe implements IRuntimeAction {
    
    private final IRecipeType recipeType;
    private final IItemStack output;
    
    public ActionRemoveOutputRecipe(IRecipeType recipeType,  IItemStack output) {
        this.recipeType = recipeType;
        this.output = output;
    }
    
    @Override
    public void apply() {
        List<ResourceLocation> list = CTRecipeManager.recipeManager.recipes.get(recipeType).values().stream().filter(iRecipe -> output.matches(new MCItemStackMutable(iRecipe.getRecipeOutput()))).map(IRecipe::getId).collect(Collectors.toList());
        list.forEach(CTRecipeManager.recipeManager.recipes.get(recipeType)::remove);
    }
    
    @Override
    public String describe() {
        return "Removing all \"" + Registry.RECIPE_TYPE.getKey(recipeType) + "\" recipes, that output: " + output;
    }
}
