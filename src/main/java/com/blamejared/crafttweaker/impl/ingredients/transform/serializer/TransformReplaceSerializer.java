package com.blamejared.crafttweaker.impl.ingredients.transform.serializer;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformReplace;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public enum TransformReplaceSerializer implements IIngredientTransformerSerializer<TransformReplace<?>> {
    INSTANCE;
    
    @Override
    public TransformReplace<?> parse(PacketBuffer buffer) {
        
        final ItemStack replaceWith = buffer.readItemStack();
        return new TransformReplace<>(new MCItemStack(replaceWith));
    }
    
    @Override
    public TransformReplace<?> parse(JsonObject json) {
        
        final Ingredient.IItemList iItemList = Ingredient.deserializeItemList(json.getAsJsonObject("replaceWith"));
        final ItemStack replaceWith = iItemList.getStacks().iterator().next();
        return new TransformReplace<>(new MCItemStack(replaceWith));
    }
    
    @Override
    public void write(PacketBuffer buffer, TransformReplace<?> ingredient) {
        
        buffer.writeItemStack(ingredient.getReplaceWith().getInternal());
    }
    
    @Override
    public JsonObject toJson(TransformReplace<?> transformer) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", getType().toString());
        jsonObject.add("replaceWith", transformer.getReplaceWith().asVanillaIngredient().serialize());
        return jsonObject;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweaker.MODID, "transform_replace");
    }
    
}