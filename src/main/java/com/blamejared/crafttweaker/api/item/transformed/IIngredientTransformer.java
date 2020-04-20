package com.blamejared.crafttweaker.api.item.transformed;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IIngredientTransformer")
@Document("vanilla/api/items/IIngredientTransformer")
public interface IIngredientTransformer<T extends IIngredient> {

    @ZenCodeType.Method
    IItemStack transform(IItemStack stack);

    @ZenCodeType.Method
    String getCommandString(T transformedIngredient);

    @SuppressWarnings("rawtypes")
    IIngredientTransformerSerializer getSerializer();

    @SuppressWarnings("unchecked")
    default void write(PacketBuffer buffer) {
        getSerializer().write(buffer, this);
    }

    @SuppressWarnings("unchecked")
    default JsonObject toJson() {
        return getSerializer().toJson(this);
    }

    default ResourceLocation getType() {
        return getSerializer().getType();
    }
}
