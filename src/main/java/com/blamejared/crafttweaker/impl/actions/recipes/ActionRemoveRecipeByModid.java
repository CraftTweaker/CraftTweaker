package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.stream.Collectors;

public class ActionRemoveRecipeByModid extends ActionRecipeBase {
    
    private final String modid;
    private final IRecipeManager.RecipeFilter exclude;
    
    public ActionRemoveRecipeByModid(IRecipeManager manager, String modid) {
        super(manager);
        this.modid = modid;
        this.exclude = name -> false;
    }
    
    public ActionRemoveRecipeByModid(IRecipeManager manager, String modid, IRecipeManager.RecipeFilter exclude) {
        super(manager);
        this.modid = modid;
        this.exclude = exclude;
    }
    
    @Override
    public void apply() {
        List<ResourceLocation> list = getManager().getRecipes().keySet().stream().filter(resourceLocation -> resourceLocation.getNamespace().equals(modid)).filter(resourceLocation -> !exclude.test(resourceLocation.getPath())).collect(Collectors.toList());
        list.forEach(getManager().getRecipes()::remove);
    }
    
    @Override
    public String describe() {
        return "Removing \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" recipes with modid: \"" + modid + "\"";
    }
    
}
