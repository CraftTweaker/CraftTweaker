package com.blamejared.crafttweaker.api.ingredients;

import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.NBTIngredient;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class PartialNBTIngredient extends Ingredient {
    
    private final ItemStack stack;
    
    public PartialNBTIngredient(ItemStack stack) {
        
        super(Stream.of(new Ingredient.SingleItemList(stack)));
        this.stack = stack;
    }
    
    @Override
    public boolean test(@Nullable ItemStack input) {
        
        if(input == null) {
            return false;
        }
        CompoundNBT stack1Tag = this.stack.getTag();
        CompoundNBT stack2Tag = input.getTag();
        if(stack1Tag == null && stack2Tag == null) {
            return true;
        }
        // Lets just use the partial nbt
        MapData stack2Data = (MapData) NBTConverter.convert(stack2Tag);
        MapData stack1Data = (MapData) NBTConverter.convert(stack1Tag);
        boolean contains;
        if(stack1Data == null) {
            contains = true;
        } else {
            contains = stack2Data != null && stack2Data.contains(stack1Data);
        }
        
        
        return this.stack.getItem() == input.getItem() && this.stack.getDamage() == input.getDamage() && contains;
    }
    
    @Override
    public boolean isSimple() {
        
        return false;
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return PartialNBTIngredient.Serializer.INSTANCE;
    }
    
    @Override
    public JsonElement serialize() {
        
        JsonObject json = new JsonObject();
        json.addProperty("type", CraftingHelper.getID(NBTIngredient.Serializer.INSTANCE).toString());
        json.addProperty("item", stack.getItem().getRegistryName().toString());
        json.addProperty("count", stack.getCount());
        if(stack.hasTag()) {
            json.addProperty("nbt", stack.getTag().toString());
        }
        return json;
    }
    
    public static class Serializer implements IIngredientSerializer<PartialNBTIngredient> {
        
        public static final PartialNBTIngredient.Serializer INSTANCE = new PartialNBTIngredient.Serializer();
        
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
            
            buffer.writeItemStack(ingredient.stack);
        }
        
    }
    
}