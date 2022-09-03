package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientPartialTagSerializer;
import com.faux.ingredientextension.api.ingredient.IngredientExtendable;
import com.faux.ingredientextension.api.ingredient.serializer.IIngredientSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class IngredientPartialTag extends IngredientExtendable {
    
    private final ItemStack stack;
    
    public IngredientPartialTag(ItemStack stack) {
        
        super(Stream.of(new Ingredient.ItemValue(stack)));
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
    public boolean requiresTesting() {
        
        return false;
    }
    
    public ItemStack getStack() {
        
        return stack;
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return IngredientPartialTagSerializer.INSTANCE;
    }
    
}
