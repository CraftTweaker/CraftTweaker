package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.impl.managers.CTRecipeManager;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.stream.Collectors;

public class ActionRemoveRecipeByModid implements IRuntimeAction {
    
    private final IRecipeType recipeType;
    private final String modid;
    
    public ActionRemoveRecipeByModid(IRecipeType recipeType, String modid) {
        this.recipeType = recipeType;
        this.modid = modid;
    }
    
    @Override
    public void apply() {
        List<ResourceLocation> list = CTRecipeManager.recipeManager.recipes.get(recipeType).keySet().stream().filter(resourceLocation -> resourceLocation.getNamespace().equals(modid)).collect(Collectors.toList());
        list.forEach(CTRecipeManager.recipeManager.recipes.get(recipeType)::remove);
    }
    
    @Override
    public String describe() {
        return "Removing \"" + Registry.RECIPE_TYPE.getKey(recipeType) + "\" recipes with modid: \"" + modid + "\"";
    }
    
}
