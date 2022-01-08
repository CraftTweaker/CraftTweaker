package com.blamejared.crafttweaker.api.ingredient.serializer;


import com.blamejared.crafttweaker.api.ingredient.type.IngredientPartialTag;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

public enum IngredientPartialTagSerializer implements IIngredientSerializer<IngredientPartialTag> {
    INSTANCE;
    
    @Override
    public IngredientPartialTag parse(FriendlyByteBuf buffer) {
        
        return new IngredientPartialTag(buffer.readItem());
    }
    
    @Override
    public IngredientPartialTag parse(JsonObject json) {
        
        return new IngredientPartialTag(CraftingHelper.getItemStack(json, true));
    }
    
    @Override
    public void write(FriendlyByteBuf buffer, IngredientPartialTag ingredient) {
        
        buffer.writeItem(ingredient.getStack());
    }
    
    
}
