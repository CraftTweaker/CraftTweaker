package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.world.item.crafting.Recipe;

public class ActionRemoveAll<T extends Recipe<?>> extends ActionRecipeBase<T> {
    
    
    public ActionRemoveAll(IRecipeManager<T> manager) {
        
        super(manager);
    }
    
    @Override
    public void apply() {
        
        getRecipeMutator().removeAll();
    }
    
    @Override
    public String describe() {
        
        return "Removing all \"" + getRecipeTypeName() + "\" recipes";
    }
    
}
