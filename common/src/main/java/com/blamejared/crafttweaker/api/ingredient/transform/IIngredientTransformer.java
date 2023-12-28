package com.blamejared.crafttweaker.api.ingredient.transform;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.serialization.Codec;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.transform.IIngredientTransformer")
@Document("vanilla/api/ingredient/transform/IIngredientTransformer")
public interface IIngredientTransformer<T extends IIngredient> {
    
    Codec<IIngredientTransformer<?>> CODEC = IIngredientTransformerSerializer.CODEC.dispatch(IIngredientTransformer::getSerializer, IIngredientTransformerSerializer::codec);
    
    @ZenCodeType.Method
    IItemStack transform(IItemStack stack);
    
    @ZenCodeType.Method
    String getCommandString(T transformedIngredient);
    
    @SuppressWarnings("rawtypes")
    IIngredientTransformerSerializer getSerializer();
    
}
