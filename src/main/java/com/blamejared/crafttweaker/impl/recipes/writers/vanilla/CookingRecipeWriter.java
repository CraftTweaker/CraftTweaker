package com.blamejared.crafttweaker.impl.recipes.writers.vanilla;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeWriter;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;

public class CookingRecipeWriter implements IRecipeWriter {
    
    private final String name;
    
    
    public CookingRecipeWriter(String name) {
        
        this.name = name;
    }
    
    @Override
    public void write(IRecipeManager manager, StringBuilder builder, IRecipe<?> recipe) {
        
        AbstractCookingRecipe abstractCookingRecipe = (AbstractCookingRecipe) recipe;
        builder.append(name).append(".addRecipe(")
                .append(StringUtils.quoteAndEscape(abstractCookingRecipe.getId())).append(", ");
        builder.append(new MCItemStackMutable(abstractCookingRecipe.getRecipeOutput()).getCommandString()).append(", ");
        builder.append(IIngredient.fromIngredient(abstractCookingRecipe.getIngredients().get(0))).append(", ");
        builder.append(abstractCookingRecipe.getExperience()).append(", ");
        builder.append(abstractCookingRecipe.getCookTime());
        builder.append(");");
    }
    
}
