package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/ingredient/type/IngredientWithAmount")
@ZenCodeType.Name("crafttweaker.api.ingredient.type.IngredientWithAmount")
public class IngredientWithAmount implements IIngredientWithAmount {
    
    private final IIngredient ingredient;
    private final int amount;
    
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
