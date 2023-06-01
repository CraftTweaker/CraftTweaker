package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientPartialTag;
import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;

public enum IngredientPartialTagSerializer implements CustomIngredientSerializer<IngredientPartialTag> {
    INSTANCE;
    
    public static final ResourceLocation ID = CraftTweakerConstants.rl("partial_tag");
    
    @Override
    public IngredientPartialTag read(FriendlyByteBuf buffer) {
        
        return new IngredientPartialTag(buffer.readItem());
    }
    
    @Override
    public void write(JsonObject json, IngredientPartialTag ingredient) {
        
        json.addProperty("item", BuiltInRegistries.ITEM.getKey(ingredient.getStack().getItem()).toString());
        json.addProperty("count", ingredient.getStack().getCount());
        if(ingredient.getStack().hasTag()) {
            json.addProperty("nbt", ingredient.getStack().getTag().toString());
        }
    }
    
    @Override
    public IngredientPartialTag read(JsonObject json) {
        
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
    public void write(FriendlyByteBuf buffer, IngredientPartialTag ingredient) {
        
        buffer.writeItem(ingredient.getStack());
    }
    
    
    @Override
    public ResourceLocation getIdentifier() {
        
        return IngredientPartialTagSerializer.ID;
    }
}
