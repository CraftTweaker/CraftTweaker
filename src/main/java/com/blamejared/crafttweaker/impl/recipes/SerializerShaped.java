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
        int height = buffer.readVarInt();
        int width = buffer.readVarInt();
        IIngredient[][] inputs = new IIngredient[height][width];
        
        for(int h = 0; h < inputs.length; h++) {
            for(int w = 0; w < inputs[h].length; w++) {
                inputs[h][w] = IIngredient.fromIngredient(Ingredient.read(buffer));
            }
        }
        
        boolean mirrored = buffer.readBoolean();
        ItemStack output = buffer.readItemStack();
        return new CTRecipeShaped(recipeId, new MCItemStack(output), inputs, mirrored, null);
    }
    
    public void write(PacketBuffer buffer, CTRecipeShaped recipe) {
        buffer.writeVarInt(recipe.getRecipeHeight());
        buffer.writeVarInt(recipe.getRecipeWidth());
        
        for(Ingredient ingredient : recipe.getIngredients()) {
            ingredient.write(buffer);
        }
        
        buffer.writeBoolean(recipe.isMirrored());
        
        buffer.writeItemStack(recipe.getRecipeOutput());
    }
}