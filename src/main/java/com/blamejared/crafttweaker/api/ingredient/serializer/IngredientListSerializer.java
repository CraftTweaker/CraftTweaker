package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.item.IngredientList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum IngredientListSerializer implements IIngredientSerializer<IngredientList> {
    INSTANCE;
    
    @Override
    public IngredientList parse(PacketBuffer buffer) {
        
        return new IngredientList(Stream.generate(() -> Ingredient.read(buffer))
                .limit(buffer.readVarInt())
                .collect(Collectors.toList()));
    }
    
    @Override
    public IngredientList parse(JsonObject json) {
        
        if(!(json.get("ingredients") instanceof JsonArray)) {
            throw new JsonParseException("No 'ingredients' array to parse!");
        }
        List<Ingredient> ingredients = new ArrayList<>();
        for(JsonElement jsonElement : json.getAsJsonArray("ingredients")) {
            ingredients.add(Ingredient.deserialize(jsonElement));
        }
        return new IngredientList(ingredients);
    }
    
    @Override
    public void write(PacketBuffer buffer, IngredientList ingredient) {
        
        CompoundIngredient.Serializer.INSTANCE.write(buffer, ingredient);
    }
    
}