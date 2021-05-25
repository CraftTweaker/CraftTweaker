package com.blamejared.crafttweaker.impl.recipes.handlers.vanilla;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@IRecipeHandler.For(ShapelessRecipe.class)
public final class ShapelessRecipeHandler implements IRecipeHandler<ShapelessRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final ShapelessRecipe recipe) {
        
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
    
    @Override
    public Optional<Function<ResourceLocation, ShapelessRecipe>> replaceIngredients(final IRecipeManager manager, final ShapelessRecipe recipe, final List<IReplacementRule> rules) {
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                rules,
                newIngredients -> id -> new ShapelessRecipe(id, recipe.getGroup(), recipe.getRecipeOutput(), newIngredients)
        );
    }
    
}
