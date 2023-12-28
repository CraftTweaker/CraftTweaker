package com.blamejared.crafttweaker.api.ingredient.condition.type;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionDamagedAtLeastSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.condition.type.ConditionDamagedAtLeast")
@Document("vanilla/api/ingredient/condition/type/ConditionDamagedAtLeast")
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
        
        return ConditionDamagedAtLeastSerializer.INSTANCE;
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
