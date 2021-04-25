package com.blamejared.crafttweaker.impl.recipes.writers.vanilla;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.StonecuttingRecipe;

public class StoneCutterRecipeHandler implements IRecipeHandler {
    
    @Override
    public void write(IRecipeManager manager, StringBuilder builder, IRecipe<?> recipe) {
        
        StonecuttingRecipe stoneCutterRecipe = (StonecuttingRecipe) recipe;
        // addRecipe(String recipeName, IItemStack output, IIngredient input)
        builder.append("stoneCutter.addRecipe(").append(StringUtils.quoteAndEscape(recipe.getId())).append(", ");
        builder.append(new MCItemStackMutable(stoneCutterRecipe.getRecipeOutput()).getCommandString()).append(", ");
        builder.append(IIngredient.fromIngredient(stoneCutterRecipe.getIngredients().get(0)).getCommandString()).append(");");
    }
    
}
