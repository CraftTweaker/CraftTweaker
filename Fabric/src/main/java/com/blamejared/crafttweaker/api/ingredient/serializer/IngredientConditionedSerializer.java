package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.ingredient.type.*;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public enum IngredientConditionedSerializer implements CustomIngredientSerializer<IngredientConditioned<?, ?>> {
    INSTANCE;
    
    @Override
    public ResourceLocation getIdentifier() {
        
        return IIngredientConditioned.ID;
    }
    
    @Override
    public IngredientConditioned<?, ?> read(JsonObject json) {
        
        final JsonObject base = json.getAsJsonObject("base");
        final IIngredient baseIngredient = IIngredient.fromIngredient(Ingredient.fromJson(base));
        
        final JsonObject condition = json.getAsJsonObject("condition");
        final ResourceLocation type = new ResourceLocation(condition.get("type").getAsString());
        final IIngredientConditionSerializer<?> value = CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        // noinspection rawtypes
        return new IngredientConditioned(new IIngredientConditioned(baseIngredient, value.fromJson(condition)));
    }
    
    @Override
    public void write(JsonObject json, IngredientConditioned<?, ?> ingredient) {
        
        json.add("base", ingredient.getCrTIngredient()
                .getBaseIngredient()
                .asVanillaIngredient().toJson());
        final IIngredientCondition<?> condition = ingredient.getCondition();
        final JsonObject value = condition.toJson();
        if(!value.has("type")) {
            value.addProperty("type", condition.getType().toString());
        }
        json.add("condition", value);
    }
    
    @Override
    public IngredientConditioned<?, ?> read(FriendlyByteBuf buffer) {
        
        final IIngredient base = IIngredient.fromIngredient(Ingredient.fromNetwork(buffer));
        final ResourceLocation type = buffer.readResourceLocation();
        final IIngredientConditionSerializer<?> value = CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        // noinspection rawtypes
        return new IngredientConditioned(new IIngredientConditioned(base, value.fromNetwork(buffer)));
    }
    
    @Override
    public void write(FriendlyByteBuf buffer, IngredientConditioned<?, ?> ingredient) {
        
        final Ingredient baseIngredient = ingredient.getCrTIngredient().getBaseIngredient().asVanillaIngredient();
        baseIngredient.toNetwork(buffer);
        
        final IIngredientCondition<?> condition = ingredient.getCondition();
        final IIngredientConditionSerializer<? extends IIngredientCondition<?>> serializer = condition.getSerializer();
        buffer.writeResourceLocation(serializer.getType());
        condition.write(buffer);
    }
}
