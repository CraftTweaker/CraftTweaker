package com.blamejared.crafttweaker.impl.recipes.handlers.vanilla;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import net.minecraft.item.crafting.SpecialRecipe;

import java.util.List;
import java.util.Optional;

@IRecipeHandler.For(SpecialRecipe.class)
public final class SpecialRecipeHandler implements IRecipeHandler<SpecialRecipe> {
    @Override
    public String dumpToCommandString(final IRecipeManager manager, final SpecialRecipe recipe) {
        return String.format(
                "~~ Special Recipe '%s' of class '%s' and with serializer '%s' cannot be represented ~~",
                recipe.getId(),
                recipe.getClass().getName(),
                recipe.getSerializer().getRegistryName()
        );
    }

    @Override
    public Optional<SpecialRecipe> replaceIngredients(final IRecipeManager manager, final SpecialRecipe recipe, final List<IReplacementRule> rules) {
        // Simply because we don't want to spam the logs for illegal replacements
        return Optional.empty();
    }
    
}
