package com.blamejared.crafttweaker.impl.script;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SerializerScript extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ScriptRecipe> {
    
    static int MAX_SERIALIZED_SCRIPT_SIZE = Short.MAX_VALUE * Short.MAX_VALUE;
    
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
        int parts = buffer.readShort();
        StringBuilder script = new StringBuilder();
        while(parts-- > 0) {
            script.append(buffer.readString());
        }
        return new ScriptRecipe(recipeId, fileName, script.toString());
    }
    
    public void write(PacketBuffer buffer, ScriptRecipe recipe) {
        
        String contents = recipe.getContent();
        if(contents.length() > MAX_SERIALIZED_SCRIPT_SIZE) {
            throw new IllegalArgumentException("Can't serialize scripts larger than " + MAX_SERIALIZED_SCRIPT_SIZE + " bytes. please split your script in smaller parts");
        }
        
        buffer.writeString(recipe.getFileName());
        
        int parts = (int) Math.ceil((double) contents.length() / (double) Short.MAX_VALUE);
        buffer.writeShort(parts);
        for(int i = 0; i < parts; i++) {
            buffer.writeString(contents.substring(i * Short.MAX_VALUE, Math.min((i + 1) * Short.MAX_VALUE, contents.length())));
        }
    }
    
}