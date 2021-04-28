package com.blamejared.crafttweaker.impl.recipes.handlers.vanilla;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.ReplacementRule;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.StonecuttingRecipe;

import java.util.List;
import java.util.Optional;

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
    
    @Override
    public Optional<StonecuttingRecipe> replaceIngredients(final IRecipeManager manager, final StonecuttingRecipe recipe, final List<ReplacementRule> rules) {
        
        final Ingredient originalInput = recipe.getIngredients().get(0);
        /*mutable*/ Ingredient input = originalInput;
        for (final ReplacementRule rule : rules) {
            if (rule.shouldReplace(input)) {
                input = rule.getVanillaTo();
            }
        }
    
        if (originalInput == input) return Optional.empty();

        return Optional.of(new StonecuttingRecipe(recipe.getId(), recipe.getGroup(), input, recipe.getRecipeOutput()));
    }
    
}
