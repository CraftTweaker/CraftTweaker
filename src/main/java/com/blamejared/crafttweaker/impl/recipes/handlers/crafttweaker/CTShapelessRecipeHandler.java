package com.blamejared.crafttweaker.impl.recipes.handlers.crafttweaker;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShapeless;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@IRecipeHandler.For(CTRecipeShapeless.class)
public final class CTShapelessRecipeHandler implements IRecipeHandler<CTRecipeShapeless> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final CTRecipeShapeless recipe) {
    
        return String.format(
                "craftingTable.addShapeless(%s, %s, %s);",
                StringUtils.quoteAndEscape(recipe.getId()),
                new MCItemStackMutable(recipe.getRecipeOutput()).getCommandString(),
                Arrays.stream(recipe.getCtIngredients())
                        .map(IIngredient::getCommandString)
                        .collect(Collectors.joining(", ", "[", "]"))
        );
    }
    
    @Override
    public Optional<CTRecipeShapeless> replaceIngredients(final IRecipeManager manager, final CTRecipeShapeless recipe, final List<IReplacementRule> rules) {
        return ReplacementHandlerHelper.replaceIIngredients(
                recipe.getCtIngredients(),
                rules,
                newIngredients -> new CTRecipeShapeless(recipe.getId().getPath(), recipe.getCtOutput(), newIngredients, recipe.getFunction())
        );
    }
    
}
