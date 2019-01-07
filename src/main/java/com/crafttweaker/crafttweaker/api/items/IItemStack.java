package com.crafttweaker.crafttweaker.api.items;

import com.crafttweaker.crafttweaker.main.ingredients.IIngredient;


public interface IItemStack extends IIngredient {
    
    IItemStack mutable();
    
    IItemStack updateTag();
    
    IItemStack withAmount(int amount);
    
    IItemStack grow(int size);
    
    IItemStack shrink(int size);
}
