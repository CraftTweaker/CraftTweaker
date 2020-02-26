package com.blamejared.crafttweaker.impl.script;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SerializerScript extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ScriptRecipe> {
    
    public ScriptRecipe read(ResourceLocation recipeId, JsonObject json) {
        // Please don't make scripts inside a datapack json 👀
        return new ScriptRecipe(recipeId, json.get("fileName").getAsString(), json.get("content").getAsString());
    }
    
    public ScriptRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        String fileName = buffer.readString();
        String firstHalf = buffer.readString();
        String secondHalf = buffer.readString();
        return new ScriptRecipe(recipeId, fileName, firstHalf + secondHalf);
    }
    
    public void write(PacketBuffer buffer, ScriptRecipe recipe) {
        buffer.writeString(recipe.getFileName());
        final int middle = recipe.getContent().length() / 2;
        String[] halves = {recipe.getContent().substring(0, middle), recipe.getContent().substring(middle)};
        buffer.writeString(halves[0]);
        buffer.writeString(halves[1]);
    }
}