package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.util.registry.Registry;

public class ActionRemoveAll extends ActionRecipeBase {
    
    
    public ActionRemoveAll(IRecipeManager manager) {
        super(manager);
    }
    
    @Override
    public void apply() {
        getManager().getRecipes().clear();
    }
    
    @Override
    public String describe() {
        return "Removing all \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" recipes";
    }
}
