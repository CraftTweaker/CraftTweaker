package com.blamejared.crafttweaker.impl.ingredients.transform;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.serializer.TransformReplaceSerializer;
import com.blamejared.crafttweaker.impl.item.MCItemStack;

public class TransformReplace<T extends IIngredient> implements IIngredientTransformer<T> {
    
    private final IItemStack replaceWith;
    
    public TransformReplace(IItemStack replaceWith) {
        
        this.replaceWith = replaceWith;
    }
    
    @Override
    public IItemStack transform(IItemStack stack) {
        
        return new MCItemStack(replaceWith.getImmutableInternal());
    }
    
    @Override
    public String getCommandString(T transformedIngredient) {
        
        return String.format("%s.transformReplace(%s)", transformedIngredient.getCommandString(), replaceWith.getCommandString());
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public IIngredientTransformerSerializer getSerializer() {
        
        return TransformReplaceSerializer.INSTANCE;
    }
    
    public IItemStack getReplaceWith() {
        
        return replaceWith;
    }
    
}
