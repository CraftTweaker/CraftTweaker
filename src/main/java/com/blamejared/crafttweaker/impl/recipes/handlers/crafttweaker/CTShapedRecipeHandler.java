package com.blamejared.crafttweaker.impl.recipes.handlers.crafttweaker;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShaped;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@IRecipeHandler.For(CTRecipeShaped.class)
public final class CTShapedRecipeHandler implements IRecipeHandler<CTRecipeShaped> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final CTRecipeShaped recipe) {
    
        final NonNullList<Ingredient> ingredients = recipe.getIngredients();
        return String.format(
                "craftingTable.addShaped(%s, %s, %s);",
                StringUtils.quoteAndEscape(recipe.getId()),
                new MCItemStackMutable(recipe.getRecipeOutput()).getCommandString(),
                Arrays.stream(recipe.getCtIngredients())
                        .map(row -> Arrays.stream(row)
                                .map(IIngredient::getCommandString)
                                .collect(Collectors.joining(", ", "[", "]")))
                        .collect(Collectors.joining(", ", "[", "]"))
        );
    }
    
    @Override
    public Optional<CTRecipeShaped> replaceIngredients(final IRecipeManager manager, final CTRecipeShaped recipe, final List<IReplacementRule> rules) {
        return ReplacementHandlerHelper.replaceIngredientArray(
                this.flatten(recipe.getCtIngredients(), recipe.getRecipeWidth(), recipe.getRecipeHeight()),
                IIngredient.class,
                rules,
                newIngredients -> new CTRecipeShaped(
                        recipe.getId().getPath(),
                        recipe.getCtOutput(),
                        this.inflate(newIngredients, recipe.getRecipeWidth(), recipe.getRecipeHeight()),
                        recipe.isMirrored(),
                        recipe.getFunction()
                )
        );
    }
    
    private IIngredient[] flatten(final IIngredient[][] ingredients, final int width, final int height) {
        final IIngredient[] flattened = new IIngredient[width * height];
        for (int i = 0; i < flattened.length; ++i) {
            flattened[i] = ingredients[i / height][i % width];
        }
        return flattened;
    }
    
    private IIngredient[][] inflate(final IIngredient[] flattened, final int width, final int height) {
        final IIngredient[][] inflated = new IIngredient[width][height];
        for (int i = 0; i < flattened.length; ++i) {
            inflated[i / height][i % width] = flattened[i];
        }
        return inflated;
    }
}
