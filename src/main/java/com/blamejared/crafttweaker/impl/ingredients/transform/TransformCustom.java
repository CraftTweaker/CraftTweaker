package com.blamejared.crafttweaker.impl.ingredients.transform;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.serializer.TransformCustomSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TransformCustom<T extends IIngredient> implements IIngredientTransformer<T> {
    
    public static final Map<String, Function<IItemStack, IItemStack>> knownTransformers = new HashMap<>();
    
    private final String uid;
    private Function<IItemStack, IItemStack> function;
    
    public TransformCustom(String uid, Function<IItemStack, IItemStack> function) {
        
        this.uid = uid;
        this.function = function;
        
        if(function != null) {
            knownTransformers.put(uid, function);
        }
    }
    
    @Override
    public IItemStack transform(IItemStack stack) {
        //TODO: Always use knownTransformers.get() and skip the function field?
        //  Reason: Multiple calls to transformCustom with the same uid and different functions
        //  would cause de-sync between client/server
        if(function == null) {
            function = knownTransformers.get(uid);
        }
        
        if(function == null) {
            throw new IllegalStateException("No transformer named '" + uid + "' known!");
        }
        
        return function.apply(stack).copy();
    }
    
    @Override
    public String getCommandString(T transformedIngredient) {
        
        return String.format("%s.transformCustom('%s')", transformedIngredient.getCommandString(), uid);
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public IIngredientTransformerSerializer getSerializer() {
        
        return TransformCustomSerializer.INSTANCE;
    }
    
    public String getUid() {
        
        return uid;
    }
    
}
