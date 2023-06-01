package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientPartialTagSerializer;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class IngredientPartialTag implements CustomIngredient {
    
    private final ItemStack stack;
    
    public IngredientPartialTag(ItemStack stack) {
        
        this.stack = stack;
    }
    
    @Override
    public boolean test(@Nullable ItemStack input) {
        
        if(input == null) {
            return false;
        }
        CompoundTag stack1Tag = this.stack.getTag();
        CompoundTag stack2Tag = input.getTag();
        if(stack1Tag == null && stack2Tag == null) {
            return true;
        }
        // Lets just use the partial nbt
        MapData stack2Data = (MapData) TagToDataConverter.convert(stack2Tag);
        MapData stack1Data = (MapData) TagToDataConverter.convert(stack1Tag);
        boolean contains;
        if(stack1Data == null) {
            contains = true;
        } else {
            contains = stack2Data != null && stack2Data.contains(stack1Data);
        }
        
        
        return this.stack.getItem() == input.getItem() && this.stack.getDamageValue() == input.getDamageValue() && contains;
    }
    
    @Override
    public List<ItemStack> getMatchingStacks() {
        
        return List.of(stack);
    }
    
    @Override
    public boolean requiresTesting() {
        
        return true;
    }
    
    public ItemStack getStack() {
        
        return stack;
    }
    
    @Override
    public CustomIngredientSerializer<IngredientPartialTag> getSerializer() {
        
        return IngredientPartialTagSerializer.INSTANCE;
    }
    
}
