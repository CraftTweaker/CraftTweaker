package com.blamejared.crafttweaker.impl.recipes;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SerializerShaped extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CTRecipeShaped> {
    
    public CTRecipeShaped read(ResourceLocation recipeId, JsonObject json) {
        // People shouldn't be making our recipes from json :eyes:
        return new CTRecipeShaped("", new MCItemStack(ItemStack.EMPTY), new IIngredient[][]{{new MCItemStack(new ItemStack(Items.ACACIA_BOAT))}}, false, null);
    }
    
    public CTRecipeShaped read(ResourceLocation recipeId, PacketBuffer buffer) {
        int width = buffer.readVarInt();
        int height = buffer.readVarInt();
        IIngredient[][] inputs = new IIngredient[width][height];
        
        for(int w = 0; w < inputs.length; w++) {
            for(int h = 0; h < inputs[w].length; h++) {
                
                inputs[w][h] = IIngredient.fromIngredient(Ingredient.read(buffer));
            }
        }
        
        boolean mirrored = buffer.readBoolean();
        ItemStack output = buffer.readItemStack();
        return new CTRecipeShaped(recipeId, new MCItemStack(output), inputs, mirrored, null);
    }
    
    public void write(PacketBuffer buffer, CTRecipeShaped recipe) {
        buffer.writeVarInt(recipe.getRecipeWidth());
        buffer.writeVarInt(recipe.getRecipeHeight());
        
        for(Ingredient ingredient : recipe.getIngredients()) {
            ingredient.write(buffer);
        }
        
        buffer.writeBoolean(recipe.isMirrored());
        
        buffer.writeItemStack(recipe.getRecipeOutput());
    }
}