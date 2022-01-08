package com.blamejared.crafttweaker.api.ingredient.condition.type;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionDamagedAtMostSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.condition.type.ConditionDamagedAtMost")
@Document("vanilla/api/ingredient/condition/type/ConditionDamagedAtMost")
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
        
        return ConditionDamagedAtMostSerializer.INSTANCE;
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
