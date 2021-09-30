package com.blamejared.crafttweaker.impl.ingredients.conditions;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientCondition;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.serializer.ConditionDamagedSerializer;

public class ConditionDamagedAtMost<T extends IIngredient> implements IIngredientCondition<T> {
    
    private final int maxDamage;
    
    public ConditionDamagedAtMost(int maxDamage) {
        
        this.maxDamage = maxDamage;
    }
    
    @Override
    public boolean matches(IItemStack stack) {
        
        return stack.getDamage() <= maxDamage;
    }
    
    @Override
    public boolean ignoresDamage() {
        
        return true;
    }
    
    @Override
    public String getCommandString(IIngredient ingredient) {
        
        return String.format("%s.onlyDamagedAtMost(%s)", ingredient.getCommandString(), maxDamage);
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
        
        ConditionDamagedAtMost<?> that = (ConditionDamagedAtMost<?>) o;
    
        return maxDamage == that.maxDamage;
    }
    
    @Override
    public int hashCode() {
        
        return maxDamage;
    }
    
    public int getMaxDamage() {
        
        return maxDamage;
    }
    
}
