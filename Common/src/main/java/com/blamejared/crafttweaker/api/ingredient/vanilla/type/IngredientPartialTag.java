package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientPartialTagSerializer;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ClassCanBeRecord")
public class IngredientPartialTag implements CraftTweakerVanillaIngredient {
    
    public static IngredientPartialTag of(ItemStack stack) {
        
        return new IngredientPartialTag(stack);
    }
    
    public static Ingredient ingredient(ItemStack children) {
        
        return Services.PLATFORM.getIngredientPartialTag(children);
    }
    
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
    
    @Override
    public boolean isEmpty() {
        
        return stack.isEmpty();
    }
    
    public ItemStack getStack() {
        
        return stack;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public IngredientPartialTagSerializer serializer() {
        
        return IngredientPartialTagSerializer.INSTANCE;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientPartialTag that = (IngredientPartialTag) o;
        return ItemStack.matches(getStack(), that.getStack());
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getStack());
    }
    
    @Override
    public String toString() {
        
        final StringBuilder sb = new StringBuilder("IngredientPartialTag{");
        sb.append("stack=").append(stack);
        sb.append('}');
        return sb.toString();
    }
    
}
