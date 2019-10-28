package com.blamejared.crafttweaker.impl.script;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SerializerScript extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ScriptRecipe> {
    
    public ScriptRecipe read(ResourceLocation recipeId, JsonObject json) {
        // Please don't make scripts inside a datapack json 👀
        return new ScriptRecipe(recipeId, json.get("fileName").getAsString(),json.get("content").getAsString());
    }
    
    public ScriptRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        String fileName = buffer.readString();
        String content = buffer.readString();
        return new ScriptRecipe(recipeId, fileName, content);
    }
    
    public void write(PacketBuffer buffer, ScriptRecipe recipe) {
        buffer.writeString(recipe.getFileName());
        buffer.writeString(recipe.getContent());
    }
}