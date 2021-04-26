package com.blamejared.crafttweaker.impl.recipes.handlers.vanilla;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@IRecipeHandler.For(ShapedRecipe.class)
public final class ShapedRecipeHandler implements IRecipeHandler<ShapedRecipe> {

    @Override
    public String dumpToCommandString(final IRecipeManager manager, final ShapedRecipe recipe) {
        
        final NonNullList<Ingredient> ingredients = recipe.getIngredients();
        return String.format(
                "craftingTable.addShaped(%s, %s, %s);",
                StringUtils.quoteAndEscape(recipe.getId()),
                new MCItemStackMutable(recipe.getRecipeOutput()).getCommandString(),
                IntStream.range(0, recipe.getRecipeHeight())
                        .mapToObj(y -> IntStream.range(0, recipe.getRecipeWidth())
                                .mapToObj(x -> ingredients.get(y * recipe.getRecipeWidth() + x))
                                .map(IIngredient::fromIngredient)
                                .map(IIngredient::getCommandString)
                                .collect(Collectors.joining(", ", "[", "]")))
                        .collect(Collectors.joining(", ", "[", "]"))
        );
    }
    
}
