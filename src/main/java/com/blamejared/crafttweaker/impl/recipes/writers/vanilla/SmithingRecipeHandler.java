package com.blamejared.crafttweaker.impl.recipes.writers.vanilla;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.SmithingRecipe;

public class SmithingRecipeHandler implements IRecipeHandler {
    
    @Override
    public void write(IRecipeManager manager, StringBuilder builder, IRecipe<?> recipe) {
        
        SmithingRecipe smithingRecipe = (SmithingRecipe) recipe;
        
        builder.append("smithing.addRecipe(").append(StringUtils.quoteAndEscape(recipe.getId())).append(", ");
        builder.append(new MCItemStackMutable(smithingRecipe.getRecipeOutput()).getCommandString()).append(", ");
        builder.append(IIngredient.fromIngredient(smithingRecipe.base).getCommandString()).append(", ");
        builder.append(IIngredient.fromIngredient(smithingRecipe.addition).getCommandString()).append(");");
        
    }
    
}
