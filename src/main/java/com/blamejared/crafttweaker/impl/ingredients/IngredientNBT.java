package com.blamejared.crafttweaker.impl.ingredients;

import com.blamejared.crafttweaker.CraftTweaker;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class IngredientNBT extends Ingredient {
    
    private final ItemStack stack;
    
    public IngredientNBT(ItemStack stack) {
        super(Stream.of(new Ingredient.SingleItemList(stack)));
        this.stack = stack;
    }
    
    @Override
    public boolean test(@Nullable ItemStack input) {
        if(input == null)
            return false;
        //Can't use areItemStacksEqualUsingNBTShareTag because it compares stack size as well
        return this.stack.getItem() == input.getItem() && this.stack.getDamage() == input.getDamage() && this.stack.areShareTagsEqual(input);
    }
    
    @Override
    public boolean isSimple() {
        return false;
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return CraftTweaker.INGREDIENT_NBT_SERIALIZER;
    }
    
    public static class Serializer implements IIngredientSerializer<IngredientNBT> {
        
        @Override
        public IngredientNBT parse(PacketBuffer buffer) {
            return new IngredientNBT(buffer.readItemStack());
        }
        
        @Override
        public IngredientNBT parse(JsonObject json) {
            return new IngredientNBT(CraftingHelper.getItemStack(json, true));
        }
        
        @Override
        public void write(PacketBuffer buffer, IngredientNBT ingredient) {
            buffer.writeItemStack(ingredient.stack);
        }
    }
}