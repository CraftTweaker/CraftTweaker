package com.crafttweaker.crafttweaker.main.ingredients;

import net.minecraft.item.crafting.Ingredient;

public interface IIngredient<T> {
    
    T getInternal();
    
    Ingredient asVanillaIngredient();
    
    String toBracketString();
    
}
