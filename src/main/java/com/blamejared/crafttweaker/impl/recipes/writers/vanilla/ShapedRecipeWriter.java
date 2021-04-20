package com.blamejared.crafttweaker.impl.recipes.writers.vanilla;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeWriter;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;

public class ShapedRecipeWriter implements IRecipeWriter {
    
    @Override
    public void write(IRecipeManager manager, StringBuilder builder, IRecipe<?> recipe) {
        
        ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
    
        builder.append("craftingTable.addShaped(")
                .append(StringUtils.quoteAndEscape(shapedRecipe.getId())).append(", ");
        builder.append(new MCItemStackMutable(shapedRecipe.getRecipeOutput()).getCommandString()).append(", ");
        builder.append("[[");
        for(int i = 1; i <= shapedRecipe.getIngredients().size(); i++) {
            Ingredient ingredient = shapedRecipe.getIngredients().get(i - 1);
            builder.append(IIngredient.fromIngredient(ingredient).getCommandString());
            if(i % shapedRecipe.getRecipeWidth() == 0) {
                builder.append("]");
                if(i >= shapedRecipe.getIngredients().size()) {
                    builder.append("]");
                } else {
                    builder.append(", [");
                }
            } else {
                builder.append(", ");
            }
        }
        builder.append(");");
        
    }
    
}
