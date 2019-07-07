package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.exceptions.ScriptException;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class ActionRemoveRecipeByOutputInput extends ActionRecipeBase {
    
    private final IItemStack output;
    private final IIngredient input;
    
    public ActionRemoveRecipeByOutputInput(IRecipeManager manager, IItemStack output, IIngredient input) {
        super(manager);
        this.output = output;
        this.input = input;
    }
    
    @Override
    public void apply() {
        List<ResourceLocation> toRemove = new ArrayList<>();
        for(ResourceLocation location : getManager().getRecipes().keySet()) {
            IRecipe<?> recipe = getManager().getRecipes().get(location);
            ItemStack recOut = (ItemStack) recipe.getRecipeOutput();
            if(output.matches(new MCItemStackMutable(recOut))) {
                for(IItemStack item : input.getItems()) {
                    if(recipe.getIngredients().get(0).test(item.getInternal())) {
                        toRemove.add(location);
                        break;
                    }
                }
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
