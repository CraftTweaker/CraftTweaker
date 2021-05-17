package com.blamejared.crafttweaker.impl.ingredients.transform;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.blamejared.crafttweaker.impl.ingredients.transform.serializer.TransformerReuseSerializer;

public class TransformReuse<T extends IIngredient> implements IIngredientTransformer<T> {
    
    
    @Override
    public IItemStack transform(IItemStack stack) {
        
        return stack.copy();
    }
    
    @Override
    public String getCommandString(T transformedIngredient) {
        
        return String.format("%s.reuse()", transformedIngredient.getCommandString());
    }
    
    @Override
    public TransformerReuseSerializer getSerializer() {
        
        return TransformerReuseSerializer.INSTANCE;
    }
    
    
    
}
