package com.blamejared.crafttweaker.api.ingredient.transform.type;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformReplaceSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.transform.type.TransformReplace")
@Document("vanilla/api/ingredient/transform/type/TransformReplace")
public record TransformReplace<T extends IIngredient>(IItemStack replaceWith) implements IIngredientTransformer<T> {
    
    @Override
    public IItemStack transform(IItemStack stack) {
        
        return IItemStack.of(replaceWith.getImmutableInternal());
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
    
}
