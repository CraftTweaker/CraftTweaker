package com.blamejared.crafttweaker.impl.recipes.writers;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeWriter;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;

import java.util.stream.Collectors;

public class DefaultRecipeWriter implements IRecipeWriter {
    
    public static IRecipeWriter INSTANCE = new DefaultRecipeWriter();
    
    @Override
    public void write(IRecipeManager manager, StringBuilder builder, IRecipe<?> recipe) {
        
        builder.append(String.format("Recipe name: %s, Outputs: %s, Inputs: [%s], Recipe Class: %s, Recipe Serializer: %s", recipe.getId(), new MCItemStackMutable(recipe
                .getRecipeOutput()).getCommandString(), recipe.getIngredients()
                .stream()
                .map(IIngredient::fromIngredient)
                .map(IIngredient::getCommandString).collect(Collectors.joining(", ")), recipe.getClass().getName(), recipe.getSerializer().getRegistryName()));
    }
    
}
