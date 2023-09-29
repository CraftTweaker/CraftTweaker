package com.blamejared.crafttweaker.api.ingredient.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.condition.IIngredientCondition")
@Document("vanilla/api/ingredient/condition/IIngredientCondition")
public interface IIngredientCondition<T extends IIngredient> {
    
    Codec<IIngredientCondition<?>> CODEC = IIngredientConditionSerializer.CODEC.dispatch(IIngredientCondition::getSerializer, IIngredientConditionSerializer::codec);
    
    @ZenCodeType.Method
    boolean matches(IItemStack stack);
    
    @ZenCodeType.Method
    String getCommandString(T ingredient);
    
    boolean ignoresDamage();
    
    IIngredientConditionSerializer getSerializer();
    
    default void write(FriendlyByteBuf buffer) {
        
        getSerializer().toNetwork(buffer, this);
    }
    
    
    default ResourceLocation getType() {
        
        return getSerializer().getType();
    }
    
}
