package com.blamejared.crafttweaker.api.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class IngredientList extends CompoundIngredient {
    
    public IngredientList(List<Ingredient> children) {
        
        super(children);
    }
    
    @Override
    public JsonElement serialize() {
        
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", CraftingHelper.getID(Serializer.INSTANCE).toString());
        jsonObject.add("ingredients", super.serialize());
        return jsonObject;
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return Serializer.INSTANCE;
    }
    
    public static class Serializer implements IIngredientSerializer<IngredientList> {
        
        public static final Serializer INSTANCE = new Serializer();
        
        @Override
        public IngredientList parse(PacketBuffer buffer) {
            
            return new IngredientList(Stream.generate(() -> Ingredient.read(buffer))
                    .limit(buffer.readVarInt())
                    .collect(Collectors.toList()));
        }
        
        @Override
        public IngredientList parse(JsonObject json) {
            
            if(!json.has("ingredients") || !(json.get("ingredients") instanceof JsonArray)) {
                throw new JsonParseException("there is not an ingredients array.");
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
    
}
