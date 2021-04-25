package com.blamejared.crafttweaker.impl.recipes.writers.crafttweaker;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;

import java.util.stream.Collectors;

public class CTShapelessRecipeHandler implements IRecipeHandler {
    
    @Override
    public void write(IRecipeManager manager, StringBuilder builder, IRecipe<?> recipe) {
    
        builder.append("craftingTable.addShapeless(").append(StringUtils.quoteAndEscape(recipe
                .getId())).append(", ");
        builder.append(new MCItemStackMutable(recipe.getRecipeOutput()).getCommandString()).append(", ");
        builder.append("[");
        builder.append(recipe.getIngredients()
                .stream()
                .map(IIngredient::fromIngredient)
                .map(IIngredient::getCommandString)
                .collect(Collectors.joining(", ")));
        builder.append("]);");
        
    }
    
}
