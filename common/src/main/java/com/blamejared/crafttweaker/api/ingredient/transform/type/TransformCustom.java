package com.blamejared.crafttweaker.api.ingredient.transform.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformCustomSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.transform.type.TransformCustom")
@Document("vanilla/api/ingredient/transform/type/TransformCustom")
public class TransformCustom<T extends IIngredient> implements IIngredientTransformer<T> {
    
    public static final Map<String, Function<IItemStack, IItemStack>> KNOWN_TRANSFORMERS = new HashMap<>();
    
    private final String uid;
    private Function<IItemStack, IItemStack> function;
    
    public TransformCustom(String uid) {
        
        this.uid = uid;
    }
    
    public TransformCustom(String uid, @Nullable Function<IItemStack, IItemStack> function) {
        
        this(uid);
        this.function = function;
        
        if(function != null) {
            KNOWN_TRANSFORMERS.put(uid, function);
        }
    }
    
    @Override
    public IItemStack transform(IItemStack stack) {
        
        if(function == null) {
            function = KNOWN_TRANSFORMERS.get(uid);
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
    
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        TransformCustom<?> that = (TransformCustom<?>) o;
        return Objects.equals(getUid(), that.getUid()) && Objects.equals(function, that.function);
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getUid(), function);
    }
    
}
