package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientTransformed;
import com.faux.ingredientextension.api.ingredient.serializer.IIngredientSerializer;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("rawtypes")
@ParametersAreNonnullByDefault
public enum IngredientTransformedSerializer implements IIngredientSerializer<IngredientTransformed<?, ?>> {
    INSTANCE;
    
    public @NotNull
    JsonObject toJson(IngredientTransformed<?, ?> ingredientVanillaPlus) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("base", ingredientVanillaPlus.getCrTIngredient()
                .getBaseIngredient()
                .asVanillaIngredient()
                .toJson());
        final IIngredientTransformer<?> transformer = ingredientVanillaPlus.getTransformer();
        final JsonObject value = transformer.toJson();
        if(!value.has("type")) {
            value.addProperty("type", transformer.getType().toString());
        }
        jsonObject.add("transformer", value);
        
        return jsonObject;
    }
    
    @Override
    public IngredientTransformed<?, ?> fromNetwork(FriendlyByteBuf buffer) {
        
        final IIngredient base = IIngredient.fromIngredient(Ingredient.fromNetwork(buffer));
        final ResourceLocation type = buffer.readResourceLocation();
        final IIngredientTransformerSerializer<?> value = CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        return new IngredientTransformed(new IIngredientTransformed(base, value.fromNetwork(buffer)));
    }
    
    @Override
    public void toJson(JsonObject json, IngredientTransformed<?, ?> ingredient) {
        
        json.add("base", ingredient.getCrTIngredient()
                .getBaseIngredient()
                .asVanillaIngredient()
                .toJson());
        final IIngredientTransformer<?> transformer = ingredient.getTransformer();
        final JsonObject value = transformer.toJson();
        if(!value.has("type")) {
            value.addProperty("type", transformer.getType().toString());
        }
        json.add("transformer", value);
        
    }
    
    @Override
    public IngredientTransformed<?, ?> fromJson(JsonObject json) {
        
        final JsonObject base = json.getAsJsonObject("base");
        final IIngredient baseIngredient = IIngredient.fromIngredient(Ingredient.fromJson(base));
        
        final JsonObject transformer = json.getAsJsonObject("transformer");
        final ResourceLocation type = new ResourceLocation(transformer.get("type").getAsString());
        final IIngredientTransformerSerializer<?> value = CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        return new IngredientTransformed(new IIngredientTransformed(baseIngredient, value.fromJson(transformer)));
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, IngredientTransformed<?, ?> ingredient) {
        
        final Ingredient baseIngredient = ingredient.getCrTIngredient().getBaseIngredient().asVanillaIngredient();
        baseIngredient.toNetwork(buffer);
        
        final IIngredientTransformer<?> transformer = ingredient.getTransformer();
        final IIngredientTransformerSerializer<? extends IIngredientTransformer<?>> serializer = transformer.getSerializer();
        buffer.writeResourceLocation(serializer.getType());
        transformer.toNetwork(buffer);
    }
}
