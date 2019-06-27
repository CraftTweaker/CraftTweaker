package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.impl.managers.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.util.registry.*;

public class ActionRemoveRecipeByName implements IRuntimeAction {
    
    
    private final IRecipeType recipeType;
    private final ResourceLocation name;
    
    public ActionRemoveRecipeByName(IRecipeType recipeType, ResourceLocation name) {
        this.recipeType = recipeType;
        this.name = name;
    }
    
    @Override
    public void apply() {
        CTRecipeManager.recipeManager.recipes.get(recipeType).remove(name);
    }
    
    @Override
    public String describe() {
        return "Removing \"" + Registry.RECIPE_TYPE.getKey(recipeType) + "\" recipe with name: \"" + name + "\"";
    }
    
    @Override
    public boolean validate(ILogger logger) {
        boolean containsKey = CTRecipeManager.recipeManager.recipes.get(recipeType).containsKey(name);
        if(!containsKey){
            logger.warning("No recipe with type: \"" + Registry.RECIPE_TYPE.getKey(recipeType) + "\" and name: \"" + name + "\"");
        }
        return containsKey;
    }
}
