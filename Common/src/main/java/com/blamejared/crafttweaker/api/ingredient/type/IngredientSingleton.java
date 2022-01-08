package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;

public interface IngredientSingleton<T extends IIngredient> {
    
    T getInstance();
    
}
