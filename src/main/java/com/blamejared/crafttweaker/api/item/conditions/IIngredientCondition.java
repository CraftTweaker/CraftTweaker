package com.blamejared.crafttweaker.api.item.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IIngredientCondition")
@Document("vanilla/api/items/IIngredientCondition")
public interface IIngredientCondition<T extends IIngredient> {
    
    @ZenCodeType.Method
    boolean matches(IItemStack stack);
    
    @ZenCodeType.Method
    String getCommandString(T ingredient);
    
    boolean ignoresDamage();
    
    @SuppressWarnings("rawtypes")
    IIngredientConditionSerializer getSerializer();
    
    default void write(PacketBuffer buffer) {
        getSerializer().write(buffer, this);
    }
    
    default JsonObject toJson() {
        return getSerializer().toJson(this);
    }
    
    default ResourceLocation getType() {
        return getSerializer().getType();
    }
}
