package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientPartialTagSerializer;
import com.blamejared.crafttweaker.platform.Services;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.StrictNBTIngredient;

import javax.annotation.Nullable;
import java.util.stream.Stream;

// Yes forge has its own PartialNBTIngredient, but using our own IData matching is much safer and ensures that we have consistent matching throughout the mod
public class IngredientPartialTag extends Ingredient {
    
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
        MapData stack2Data = TagToDataConverter.convertCompound(stack2Tag);
        MapData stack1Data = TagToDataConverter.convertCompound(stack1Tag);
        boolean contains;
        if(stack1Data == null) {
            contains = true;
        } else {
            contains = stack2Data != null && stack2Data.contains(stack1Data);
        }
        
        return this.stack.getItem() == input.getItem() && this.stack.getDamageValue() == input.getDamageValue() && contains;
    }
    
    @Override
    public boolean isSimple() {
        
        return false;
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return IngredientPartialTagSerializer.INSTANCE;
    }
    
    public ItemStack getStack() {
        
        return stack;
    }
    
    
    @Override
    public JsonElement toJson() {
        
        JsonObject json = new JsonObject();
        json.addProperty("type", CraftingHelper.getID(IngredientPartialTagSerializer.INSTANCE).toString());
        json.addProperty("item", Services.REGISTRY.getRegistryKey(stack.getItem()).toString());
        json.addProperty("count", stack.getCount());
        if(stack.hasTag()) {
            json.addProperty("nbt", stack.getTag().toString());
        }
        return json;
    }
    
}