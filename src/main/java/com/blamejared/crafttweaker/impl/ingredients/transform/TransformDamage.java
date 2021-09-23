package com.blamejared.crafttweaker.impl.ingredients.transform;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.serializer.TransformDamageSerializer;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.item.ItemStack;

public class TransformDamage<T extends IIngredient> implements IIngredientTransformer<T> {
    
    private final int amount;
    
    public TransformDamage(int amount) {
        
        this.amount = amount;
    }
    
    public int getAmount() {
        
        return amount;
    }
    
    @Override
    public IItemStack transform(IItemStack stack) {
        
        final ItemStack internal = stack.getImmutableInternal();
        final int newDamage = internal.getDamage() + amount;
        if(internal.getMaxDamage() < newDamage) {
            return MCItemStack.EMPTY.get();
        }
        internal.setDamage(newDamage);
        return new MCItemStack(internal.copy());
    }
    
    @Override
    public String getCommandString(T transformedIngredient) {
        
        if(this.amount == 1) {
            return transformedIngredient.getCommandString() + ".transformDamage()";
        }
        return String.format("%s.transformDamage(%s)", transformedIngredient.getCommandString(), amount);
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public IIngredientTransformerSerializer getSerializer() {
        
        return TransformDamageSerializer.INSTANCE;
    }
    
    
}
