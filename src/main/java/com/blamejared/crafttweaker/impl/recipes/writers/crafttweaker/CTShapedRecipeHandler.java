package com.blamejared.crafttweaker.impl.recipes.writers.crafttweaker;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShaped;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;

public class CTShapedRecipeHandler implements IRecipeHandler {
    
    @Override
    public void write(IRecipeManager manager, StringBuilder builder, IRecipe<?> recipe) {
        
        CTRecipeShaped shapedRecipe = (CTRecipeShaped) recipe;
        
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
