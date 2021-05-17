package com.blamejared.crafttweaker.impl.ingredients.conditions;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientCondition;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.serializer.ConditionAnyDamagedSerializer;

public class ConditionAnyDamage<T extends IIngredient> implements IIngredientCondition<T> {
    
    @Override
    public boolean matches(IItemStack stack) {
        return true;
    }
    
    @Override
    public boolean ignoresDamage() {
        return true;
    }
    
    @Override
    public String getCommandString(IIngredient ingredient) {
        return ingredient.getCommandString() + ".anyDamage()";
    }
    
    @Override
    public IIngredientConditionSerializer getSerializer() {
        return ConditionAnyDamagedSerializer.INSTANCE;
    }
    
    
}
