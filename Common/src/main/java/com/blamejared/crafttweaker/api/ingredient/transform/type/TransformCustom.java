package com.blamejared.crafttweaker.api.ingredient.transform.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformCustomSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.transform.type.TransformCustom")
@Document("vanilla/api/ingredient/transform/type/TransformCustom")
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
