package com.blamejared.crafttweaker.impl.item.transformed;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IngredientTransformedSerializer implements IIngredientSerializer<IngredientTransformed<?, ?>> {

    public JsonObject toJson(IngredientTransformed<?, ?> ingredientVanillaPlus) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("base", ingredientVanillaPlus.getCrTIngredient().getBaseIngredient().asVanillaIngredient().serialize());
        final IIngredientTransformer<?> transformer = ingredientVanillaPlus.getTransformer();
        final JsonObject value = transformer.toJson();
        if(!value.has("type")) {
            value.addProperty("type", transformer.getType().toString());
        }
        jsonObject.add("transformer", value);

        return jsonObject;
    }

    @Override
    public IngredientTransformed<?, ?> parse(PacketBuffer buffer) {
        final IIngredient base = IIngredient.fromIngredient(Ingredient.read(buffer));
        final ResourceLocation type = buffer.readResourceLocation();
        final IIngredientTransformerSerializer<?> value = CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER.getOrDefault(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

        // noinspection rawtypes,unchecked
        return new IngredientTransformed(new MCIngredientTransformed(base, value.parse(buffer)));
    }

    @Override
    public IngredientTransformed<?, ?> parse(JsonObject json) {
        final JsonObject base = json.getAsJsonObject("base");
        final IIngredient baseIngredient = IIngredient.fromIngredient(CraftingHelper.getIngredient(base));

        final JsonObject transformer = json.getAsJsonObject("transformer");
        final ResourceLocation type = new ResourceLocation(transformer.get("type").getAsString());
        final IIngredientTransformerSerializer<?> value = CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER.getOrDefault(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

        // noinspection rawtypes,unchecked
        return new IngredientTransformed(new MCIngredientTransformed(baseIngredient, value.parse(transformer)));
    }

    @Override
    public void write(PacketBuffer buffer, IngredientTransformed<?, ?> ingredient) {
        final Ingredient baseIngredient = ingredient.getCrTIngredient().getBaseIngredient().asVanillaIngredient();
        baseIngredient.write(buffer);

        final IIngredientTransformer<?> transformer = ingredient.getTransformer();
        final IIngredientTransformerSerializer<? extends IIngredientTransformer<?>> serializer = transformer.getSerializer();
        buffer.writeResourceLocation(serializer.getType());
        transformer.write(buffer);
    }
}
