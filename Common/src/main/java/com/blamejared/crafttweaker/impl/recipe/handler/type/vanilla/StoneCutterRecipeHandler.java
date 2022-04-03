package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@IRecipeHandler.For(StonecutterRecipe.class)
public final class StoneCutterRecipeHandler implements IRecipeHandler<StonecutterRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final StonecutterRecipe recipe) {
        
        return String.format(
                "stoneCutter.addRecipe(%s, %s, %s);",
                StringUtil.quoteAndEscape(recipe.getId()),
                ItemStackUtil.getCommandString(recipe.getResultItem()),
                IIngredient.fromIngredient(recipe.getIngredients().get(0)).getCommandString()
        );
    }
    
    @Override
    public Optional<Function<ResourceLocation, StonecutterRecipe>> replaceIngredients(final IRecipeManager manager, final StonecutterRecipe recipe, final List<IReplacementRule> rules) {
        
        return IRecipeHandler.attemptReplacing(recipe.getIngredients().get(0), Ingredient.class, recipe, rules)
                .map(input -> id -> new StonecutterRecipe(id, recipe.getGroup(), input, recipe.getResultItem()));
    }
    
}
