package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.ingredient.type.IngredientList;
import com.faux.ingredientextension.api.ingredient.serializer.IIngredientSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum IngredientListSerializer implements IIngredientSerializer<IngredientList> {
    INSTANCE;
    
    @Override
    public IngredientList fromNetwork(FriendlyByteBuf buffer) {
        
        return new IngredientList(Stream.generate(() -> Ingredient.fromNetwork(buffer))
                .limit(buffer.readVarInt())
                .collect(Collectors.toList()));
    }
    
    @Override
    public void toJson(JsonObject json, IngredientList ingredient) {
        
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
    public IngredientList fromJson(JsonObject json) {
        
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
    public void toNetwork(FriendlyByteBuf buffer, IngredientList ingredient) {
        
        buffer.writeVarInt(ingredient.getChildren().size());
        ingredient.getChildren().forEach(c -> c.toNetwork(buffer));
    }
    
}