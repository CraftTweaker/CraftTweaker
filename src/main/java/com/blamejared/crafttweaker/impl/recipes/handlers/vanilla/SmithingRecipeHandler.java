package com.blamejared.crafttweaker.impl.recipes.handlers.vanilla;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.SmithingRecipe;

@IRecipeHandler.For(SmithingRecipe.class)
public final class SmithingRecipeHandler implements IRecipeHandler<SmithingRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final SmithingRecipe recipe) {
        
        return String.format(
                "smithing.addRecipe(%s, %s, %s, %s);",
                StringUtils.quoteAndEscape(recipe.getId()),
                new MCItemStackMutable(recipe.getRecipeOutput()).getCommandString(),
                IIngredient.fromIngredient(recipe.base).getCommandString(),
                IIngredient.fromIngredient(recipe.addition).getCommandString()
        );
    }
    
}
