package com.blamejared.crafttweaker.impl.recipes.handlers.crafttweaker;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShapeless;
import com.blamejared.crafttweaker.impl.recipes.helper.CraftingTableRecipeConflictChecker;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@IRecipeHandler.For(CTRecipeShapeless.class)
public final class CTShapelessRecipeHandler implements IRecipeHandler<CTRecipeShapeless> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final CTRecipeShapeless recipe) {
        
        return String.format(
                "craftingTable.addShapeless(%s, %s, %s%s);",
                StringUtils.quoteAndEscape(recipe.getId()),
                recipe.getCtOutput().getCommandString(),
                Arrays.stream(recipe.getCtIngredients())
                        .map(IIngredient::getCommandString)
                        .collect(Collectors.joining(", ", "[", "]")),
                recipe.getFunction() == null ? "" : ", (usualOut, inputs) => { ... }"
        );
    }
    
    @Override
    public Optional<Function<ResourceLocation, CTRecipeShapeless>> replaceIngredients(final IRecipeManager manager, final CTRecipeShapeless recipe, final List<IReplacementRule> rules) {
        
        return ReplacementHandlerHelper.replaceIngredientArray(
                recipe.getCtIngredients(),
                IIngredient.class,
                recipe,
                rules,
                newIngredients -> id -> new CTRecipeShapeless(id.getPath(), recipe.getCtOutput(), newIngredients, recipe.getFunction())
        );
    }
    
    @Override
    public <U extends IRecipe<?>> boolean isThereConflictBetween(final IRecipeManager manager, final CTRecipeShapeless firstRecipe, final U secondRecipe) {
        
        return CraftingTableRecipeConflictChecker.checkConflicts(manager, firstRecipe, secondRecipe);
    }
    
}
