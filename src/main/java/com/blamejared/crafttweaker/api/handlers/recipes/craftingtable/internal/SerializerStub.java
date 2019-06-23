package com.blamejared.crafttweaker.api.handlers.recipes.craftingtable.internal;

import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SerializerStub<T extends ICraftingRecipe> implements IRecipeSerializer<T> {
    
    private final T recipe;
    
    public SerializerStub(T recipe) {
        this.recipe = recipe;
    }
    
    
    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {
        return recipe;
    }
    
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
        return recipe;
    }
    
    @Override
    public void write(PacketBuffer buffer, T recipe) {
    
    }
    
    @Override
    public IRecipeSerializer<?> setRegistryName(ResourceLocation name) {
        return this;
    }
    
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getId();
    }
    
    @Override
    public Class<IRecipeSerializer<?>> getRegistryType() {
        //TODO: Just. make. it. work.
        return field_222157_a.getRegistryType();
    }
    
}
