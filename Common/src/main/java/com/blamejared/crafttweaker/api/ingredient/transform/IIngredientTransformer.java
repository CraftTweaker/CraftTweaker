package com.blamejared.crafttweaker.api.ingredient.transform;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.transform.IIngredientTransformer")
@Document("vanilla/api/ingredient/transform/IIngredientTransformer")
public interface IIngredientTransformer<T extends IIngredient> {
    
    @ZenCodeType.Method
    IItemStack transform(IItemStack stack);
    
    @ZenCodeType.Method
    String getCommandString(T transformedIngredient);
    
    @SuppressWarnings("rawtypes")
    IIngredientTransformerSerializer getSerializer();
    
    default void toNetwork(FriendlyByteBuf buffer) {
        
        getSerializer().toNetwork(buffer, this);
    }
    
    default JsonObject toJson() {
        
        return getSerializer().toJson(this);
    }
    
    default ResourceLocation getType() {
        
        return getSerializer().getType();
    }
    
}
