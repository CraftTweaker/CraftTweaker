package com.blamejared.crafttweaker.impl.ingredients.conditions;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientCondition;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.serializer.ConditionDamagedSerializer;

public class ConditionDamaged<T extends IIngredient> implements IIngredientCondition<T> {
    
    @Override
    public boolean matches(IItemStack stack) {
        
        return stack.getDamage() > 0;
    }
    
    @Override
    public boolean ignoresDamage() {
        
        return true;
    }
    
    @Override
    public String getCommandString(IIngredient ingredient) {
        
        return ingredient.getCommandString() + ".onlyDamaged()";
    }
    
    @Override
    public IIngredientConditionSerializer getSerializer() {
        
        return ConditionDamagedSerializer.INSTANCE;
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof ConditionDamaged;
    }
    
    @Override
    public int hashCode() {
        return ConditionDamaged.class.hashCode();
    }
    
}
