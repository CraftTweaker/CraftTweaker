package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.ingredient.PartialNBTIngredient;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

public enum PartialNBTIngredientSerializer implements IIngredientSerializer<PartialNBTIngredient> {
    INSTANCE;
    
    @Override
    public PartialNBTIngredient parse(PacketBuffer buffer) {
        
        return new PartialNBTIngredient(buffer.readItemStack());
    }
    
    @Override
    public PartialNBTIngredient parse(JsonObject json) {
        
        return new PartialNBTIngredient(CraftingHelper.getItemStack(json, true));
    }
    
    @Override
    public void write(PacketBuffer buffer, PartialNBTIngredient ingredient) {
        
        buffer.writeItemStack(ingredient.getStack());
    }
    
    
}
