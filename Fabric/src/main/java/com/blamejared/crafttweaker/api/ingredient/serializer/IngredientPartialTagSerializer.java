package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.ingredient.type.IngredientPartialTag;
import com.blamejared.crafttweaker.platform.Services;
import com.faux.ingredientextension.api.ingredient.serializer.IIngredientSerializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;

public enum IngredientPartialTagSerializer implements IIngredientSerializer<IngredientPartialTag> {
    INSTANCE;
    
    @Override
    public IngredientPartialTag fromNetwork(FriendlyByteBuf buffer) {
        
        return new IngredientPartialTag(buffer.readItem());
    }
    
    @Override
    public void toJson(JsonObject json, IngredientPartialTag ingredient) {
        
        json.addProperty("item", Services.REGISTRY.getRegistryKey(ingredient.getStack().getItem()).toString());
        json.addProperty("count", ingredient.getStack().getCount());
        if(ingredient.getStack().hasTag()) {
            json.addProperty("nbt", ingredient.getStack().getTag().toString());
        }
    }
    
    @Override
    public IngredientPartialTag fromJson(JsonObject json) {
        
        ItemStack stack = ShapedRecipe.itemStackFromJson(json);
        if(json.has("nbt")) {
            try {
                CompoundTag compoundtag = TagParser.parseTag(GsonHelper.convertToString(json.get("nbt"), "nbt"));
                stack.setTag(compoundtag);
            } catch(CommandSyntaxException commandsyntaxexception) {
                throw new JsonSyntaxException("Invalid nbt tag: " + commandsyntaxexception.getMessage());
            }
        }
        
        return new IngredientPartialTag(stack);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, IngredientPartialTag ingredient) {
        
        buffer.writeItem(ingredient.getStack());
    }
    
    
}
