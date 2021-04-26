package com.blamejared.crafttweaker.impl.recipes.handlers.vanilla;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.StonecuttingRecipe;

@IRecipeHandler.For(StonecuttingRecipe.class)
public final class StoneCutterRecipeHandler implements IRecipeHandler<StonecuttingRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final StonecuttingRecipe recipe) {
        
        return String.format(
                "stoneCutter.addRecipe(%s, %s, %s);",
                StringUtils.quoteAndEscape(recipe.getId()),
                new MCItemStackMutable(recipe.getRecipeOutput()).getCommandString(),
                IIngredient.fromIngredient(recipe.getIngredients().get(0)).getCommandString()
        );
    }
    
}
