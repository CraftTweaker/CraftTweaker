package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.exceptions.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.managers.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.util.registry.*;

import java.util.*;

public class ActionRemoveRecipeByOutput implements IRuntimeAction {
    
    private final IRecipeType recipeType;
    private final IItemStack output;
    
    public ActionRemoveRecipeByOutput(IRecipeType recipeType, IItemStack output) {
        this.recipeType = recipeType;
        this.output = output;
    }
    
    @Override
    public void apply() {
        List<ResourceLocation> toRemove = new ArrayList<>();
        for(ResourceLocation location : CTRecipeManager.recipeManager.recipes.get(recipeType).keySet()) {
            ItemStack recOut = CTRecipeManager.recipeManager.recipes.get(recipeType).get(location).getRecipeOutput();
            if(output.matches(new MCItemStackMutable(recOut))) {
                toRemove.add(location);
            }
        }
        toRemove.forEach(CTRecipeManager.recipeManager.recipes.get(recipeType)::remove);
        
    }
    
    @Override
    public String describe() {
        return "Removing \"" + Registry.RECIPE_TYPE.getKey(recipeType) + "\" recipes with output: " + output + "\"";
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(output == null) {
            logger.throwingWarn("output cannot be null!", new ScriptException("output IItemStack cannot be null!"));
            return false;
        }
        return true;
    }
}
