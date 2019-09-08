package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.exceptions.ScriptException;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class ActionRemoveRecipeByOutput extends ActionRecipeBase {
    
    private final IItemStack output;
    
    public ActionRemoveRecipeByOutput(IRecipeManager manager, IItemStack output) {
        super(manager);
        this.output = output;
    }
    
    @Override
    public void apply() {
        List<ResourceLocation> toRemove = new ArrayList<>();
        for(ResourceLocation location : getManager().getRecipes().keySet()) {
            ItemStack recOut = getManager().getRecipes().get(location).getRecipeOutput();
            if(output.matches(new MCItemStackMutable(recOut))) {
                toRemove.add(location);
            }
        }
        toRemove.forEach(getManager().getRecipes()::remove);
        
    }
    
    @Override
    public String describe() {
        return "Removing \"" + Registry.RECIPE_TYPE.getKey(getManager().getRecipeType()) + "\" recipes with output: " + output + "\"";
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
