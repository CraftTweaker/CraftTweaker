package com.blamejared.crafttweaker.api.ingredient.condition.type;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionDamagedSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.condition.type.ConditionDamaged")
@Document("vanilla/api/ingredient/condition/type/ConditionDamaged")
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
