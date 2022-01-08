package com.blamejared.crafttweaker.impl.script;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.LinkedList;

public interface IScriptSerializer extends RecipeSerializer<ScriptRecipe> {
    
    @Override
    default ScriptRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        // Please don't make scripts inside a datapack json 👀
        return new ScriptRecipe(recipeId, json.get("fileName").getAsString(), json.get("content").getAsString());
    }
    
    @Override
    default ScriptRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        
        String fileName = buffer.readUtf();
        int parts = buffer.readVarInt();
        StringBuilder script = new StringBuilder();
        while(parts-- > 0) {
            script.append(buffer.readUtf());
        }
        return new ScriptRecipe(recipeId, fileName, script.toString());
    }
    
    @Override
    default void toNetwork(FriendlyByteBuf buffer, ScriptRecipe recipe) {
        
        String contents = recipe.getContent();
        LinkedList<String> split = Lists.newLinkedList(Splitter.fixedLength(Short.MAX_VALUE / Byte.SIZE)
                .split(contents));
        buffer.writeUtf(recipe.getFileName());
        buffer.writeVarInt(split.size());
        split.forEach(buffer::writeUtf);
    }
    
}
