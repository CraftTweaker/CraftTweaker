package com.blamejared.crafttweaker.impl.script;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.LinkedList;

public class SerializerScript extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ScriptRecipe> {
    
    public static final SerializerScript INSTANCE = new SerializerScript();
    
    public SerializerScript() {
        
        setRegistryName(new ResourceLocation("crafttweaker:scripts"));
    }
    
    public ScriptRecipe read(ResourceLocation recipeId, JsonObject json) {
        // Please don't make scripts inside a datapack json 👀
        return new ScriptRecipe(recipeId, json.get("fileName").getAsString(), json.get("content").getAsString());
    }
    
    public ScriptRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        
        String fileName = buffer.readString();
        int parts = buffer.readVarInt();
        StringBuilder script = new StringBuilder();
        while(parts-- > 0) {
            script.append(buffer.readString());
        }
        return new ScriptRecipe(recipeId, fileName, script.toString());
    }
    
    public void write(PacketBuffer buffer, ScriptRecipe recipe) {
        
        String contents = recipe.getContent();
        LinkedList<String> split = Lists.newLinkedList(Splitter.fixedLength(Short.MAX_VALUE / Byte.SIZE).split(contents));
        buffer.writeString(recipe.getFileName());
        buffer.writeVarInt(split.size());
        split.forEach(buffer::writeString);
    }
    
}