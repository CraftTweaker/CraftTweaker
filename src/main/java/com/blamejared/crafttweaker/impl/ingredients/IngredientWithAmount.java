package com.blamejared.crafttweaker.impl.ingredients;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IIngredientWithAmount;

public class IngredientWithAmount implements IIngredientWithAmount {
    
    private final IIngredient ingredient;
    private int amount;
    
    public IngredientWithAmount(IIngredient ingredient, int amount) {
        
        this.ingredient = ingredient;
        this.amount = amount;
    }
    
    @Override
    public String getCommandString() {
        
        if(amount == 1) {
            return ingredient.getCommandString();
        }
        return String.format("(%s) * %d", ingredient.getCommandString(), amount);
    }
    
    @Override
    public IIngredient getIngredient() {
        
        return ingredient;
    }
    
    @Override
    public int getAmount() {
        
        return amount;
    }
    
}
