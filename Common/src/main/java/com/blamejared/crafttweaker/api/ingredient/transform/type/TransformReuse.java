package com.blamejared.crafttweaker.api.ingredient.transform.type;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformReuseSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.transform.type.TransformReuse")
@Document("vanilla/api/ingredient/transform/type/TransformReuse")
public class TransformReuse<T extends IIngredient> implements IIngredientTransformer<T> {
    
    public static final TransformReuse<?> INSTANCE_RAW = new TransformReuse<>();
    
    public static <T extends IIngredient> TransformReuse<T> getInstance() {
        
        return GenericUtil.uncheck(INSTANCE_RAW);
    }
    
    private TransformReuse() {}
    
    @Override
    public IItemStack transform(IItemStack stack) {
        
        return stack.copy().setAmount(1);
    }
    
    @Override
    public String getCommandString(T transformedIngredient) {
        
        return String.format("%s.reuse()", transformedIngredient.getCommandString());
    }
    
    @Override
    public TransformReuseSerializer getSerializer() {
        
        return TransformReuseSerializer.INSTANCE;
    }
    
    
    
}
