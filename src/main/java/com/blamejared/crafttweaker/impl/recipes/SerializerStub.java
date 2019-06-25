package com.blamejared.crafttweaker.impl.recipes;

import com.google.gson.*;
import mcp.*;
import net.minecraft.item.crafting.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

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
        return CRAFTING_SHAPED.getRegistryType();
    }
    
}
