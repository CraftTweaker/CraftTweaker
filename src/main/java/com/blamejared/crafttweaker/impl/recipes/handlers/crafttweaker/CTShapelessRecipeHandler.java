package com.blamejared.crafttweaker.impl.recipes.handlers.crafttweaker;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShapeless;

import java.util.stream.Collectors;

@IRecipeHandler.For(CTRecipeShapeless.class)
public final class CTShapelessRecipeHandler implements IRecipeHandler<CTRecipeShapeless> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final CTRecipeShapeless recipe) {
    
        return String.format(
                "craftingTable.addShapeless(%s, %s, %s);",
                StringUtils.quoteAndEscape(recipe.getId()),
                new MCItemStackMutable(recipe.getRecipeOutput()).getCommandString(),
                recipe.getIngredients().stream()
                        .map(IIngredient::fromIngredient)
                        .map(IIngredient::getCommandString)
                        .collect(Collectors.joining(", ", "[", "]"))
        );
    }
    
}
