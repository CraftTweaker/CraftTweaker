package com.blamejared.crafttweaker.impl.ingredients.conditions;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientCondition;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.serializer.ConditionDamagedSerializer;

public class ConditionDamagedAtLeast<T extends IIngredient> implements IIngredientCondition<T> {
    
    private final int minDamage;
    
    public ConditionDamagedAtLeast(int minDamage) {
        
        this.minDamage = minDamage;
    }
    
    @Override
    public boolean matches(IItemStack stack) {
        
        return stack.getDamage() >= minDamage;
    }
    
    @Override
    public boolean ignoresDamage() {
        
        return true;
    }
    
    @Override
    public String getCommandString(IIngredient ingredient) {
        
        return String.format("%s.onlyDamagedAtLeast(%s)", ingredient.getCommandString(), minDamage);
    }
    
    @Override
    public IIngredientConditionSerializer getSerializer() {
        
        return ConditionDamagedSerializer.INSTANCE;
    }
    
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        ConditionDamagedAtLeast<?> that = (ConditionDamagedAtLeast<?>) o;
    
        return minDamage == that.minDamage;
    }
    
    @Override
    public int hashCode() {
        
        return minDamage;
    }
    
    public int getMinDamage() {
        
        return minDamage;
    }
    
}
