package com.blamejared.crafttweaker.api.ingredient.serializer;


import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientTransformed;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public enum IngredientTransformedSerializer implements IIngredientSerializer<IngredientTransformed<?, ?>> {
    INSTANCE;
    
    public JsonObject toJson(IngredientTransformed<?, ?> ingredientVanillaPlus) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", CraftingHelper.getID(IngredientTransformedSerializer.INSTANCE).toString());
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
    public IngredientTransformed<?, ?> parse(FriendlyByteBuf buffer) {
        
        final IIngredient base = IIngredient.fromIngredient(Ingredient.fromNetwork(buffer));
        final ResourceLocation type = buffer.readResourceLocation();
        final IIngredientTransformerSerializer<?> value = CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        // noinspection rawtypes
        return new IngredientTransformed(new IIngredientTransformed(base, value.fromNetwork(buffer)));
    }
    
    @Override
    public IngredientTransformed<?, ?> parse(JsonObject json) {
        
        final JsonObject base = json.getAsJsonObject("base");
        final IIngredient baseIngredient = IIngredient.fromIngredient(CraftingHelper.getIngredient(base));
        
        final JsonObject transformer = json.getAsJsonObject("transformer");
        final ResourceLocation type = new ResourceLocation(transformer.get("type").getAsString());
        final IIngredientTransformerSerializer<?> value = CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        // noinspection rawtypes
        return new IngredientTransformed(new IIngredientTransformed(baseIngredient, value.fromJson(transformer)));
    }
    
    @Override
    public void write(FriendlyByteBuf buffer, IngredientTransformed<?, ?> ingredient) {
        
        final Ingredient baseIngredient = ingredient.getCrTIngredient().getBaseIngredient().asVanillaIngredient();
        baseIngredient.toNetwork(buffer);
        
        final IIngredientTransformer<?> transformer = ingredient.getTransformer();
        final IIngredientTransformerSerializer<? extends IIngredientTransformer<?>> serializer = transformer.getSerializer();
        buffer.writeResourceLocation(serializer.getType());
        transformer.toNetwork(buffer);
    }
}
