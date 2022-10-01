package com.blamejared.crafttweaker.impl.actions.recipes.generic;

import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.item.crafting.IRecipe;

public class ActionRemoveGenericRecipeByInput extends ActionRemoveGenericRecipeBase {

    private final IItemStack input;

    public ActionRemoveGenericRecipeByInput(IItemStack input) {

        this.input = input;
    }

    @Override
    public String describe() {

        return "Removing all recipes with an input of  " + input.getCommandString();
    }

    @Override
    protected boolean shouldRemove(IRecipe<?> recipe) {

        return recipe.getIngredients()
                .stream()
                .anyMatch(ingredient -> ingredient.test(input.getInternal()));
    }

}
