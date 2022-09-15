package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.Objects;

public final class BuiltinRecipeComponents {
    
    public static final IRecipeComponent<IIngredient> INPUT_INGREDIENT = IRecipeComponent.composite(
            CraftTweakerConstants.rl("input_ingredient"),
            new TypeToken<>() {},
            (a, b) -> Objects.equals(a, b) || (a.contains(b) && b.contains(a)),
            ingredient -> Arrays.asList(ingredient.getItems()),
            items -> items.size() < 1? IIngredientEmpty.getInstance() : items.stream().reduce(IIngredient::or).orElseThrow()
    );
    
    public static final IRecipeComponent<IItemStack> OUTPUT_ITEM = IRecipeComponent.simple(
            CraftTweakerConstants.rl("output_item"),
            new TypeToken<>() {},
            (a, b) -> ItemStackUtil.areStacksTheSame(a.getInternal(), b.getInternal())
    );
    
}
