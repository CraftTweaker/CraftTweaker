package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.ingredient.type.*;
import com.google.gson.*;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.*;
import java.util.stream.*;

public enum IngredientListSerializer implements CustomIngredientSerializer<IngredientList> {
    INSTANCE;
    
    @Override
    public IngredientList read(FriendlyByteBuf buffer) {
        
        return new IngredientList(Stream.generate(() -> Ingredient.fromNetwork(buffer))
                .limit(buffer.readVarInt())
                .collect(Collectors.toList()));
    }
    
    @Override
    public void write(JsonObject json, IngredientList ingredient) {
        
        JsonElement element;
        if(ingredient.getChildren().size() == 1) {
            element = ingredient.getChildren().get(0).toJson();
        } else {
            element = new JsonArray();
            JsonArray elementArray = (JsonArray) element;
            ingredient.getChildren().forEach(e -> elementArray.add(e.toJson()));
        }
        json.add("ingredients", element);
    }
    
    @Override
    public ResourceLocation getIdentifier() {
        
        return IIngredientList.ID;
    }
    
    @Override
    public IngredientList read(JsonObject json) {
        
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
        
        buffer.writeVarInt(ingredient.getChildren().size());
        ingredient.getChildren().forEach(c -> c.toNetwork(buffer));
    }
    
}