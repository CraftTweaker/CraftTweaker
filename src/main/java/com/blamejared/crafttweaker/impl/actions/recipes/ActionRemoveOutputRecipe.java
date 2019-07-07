package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.stream.Collectors;

public class ActionRemoveOutputRecipe extends ActionRecipeBase {
    
    private final IItemStack output;
    
    public ActionRemoveOutputRecipe(IRecipeManager manager, IItemStack output) {
        super(manager);
        this.output = output;
    }
    
    @Override
    public void apply() {
        List<ResourceLocation> list = getManager().getRecipes().values().stream().filter(iRecipe -> output.matches(new MCItemStackMutable(iRecipe.getRecipeOutput()))).map(IRecipe::getId).collect(Collectors.toList());
        list.forEach(getManager().getRecipes()::remove);
    }
    
    @Override
    public String describe() {
        return "Removing all \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" recipes, that output: " + output;
    }
}
