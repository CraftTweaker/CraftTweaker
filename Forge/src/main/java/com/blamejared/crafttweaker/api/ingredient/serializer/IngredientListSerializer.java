package com.blamejared.crafttweaker.api.ingredient.serializer;


import com.blamejared.crafttweaker.api.ingredient.type.IngredientList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum IngredientListSerializer implements IIngredientSerializer<IngredientList> {
    INSTANCE;
    
    @Override
    public IngredientList parse(FriendlyByteBuf buffer) {
        
        return new IngredientList(Stream.generate(() -> Ingredient.fromNetwork(buffer))
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
            ingredients.add(Ingredient.fromJson(jsonElement));
        }
        return new IngredientList(ingredients);
    }
    
    @Override
    public void write(FriendlyByteBuf buffer, IngredientList ingredient) {
        
        CompoundIngredient.Serializer.INSTANCE.write(buffer, ingredient);
    }
    
}