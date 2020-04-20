package com.blamejared.crafttweaker.impl.ingredients.transform;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TransformCustom<T extends IIngredient> implements IIngredientTransformer<T> {

    public static final Map<String, Function<IItemStack, IItemStack>> knownTransformers = new HashMap<>();

    private final String uid;
    private Function<IItemStack, IItemStack> function;

    public TransformCustom(String uid, Function<IItemStack, IItemStack> function) {
        this.uid = uid;
        this.function = function;

        if (function != null) {
            knownTransformers.put(uid, function);
        }
    }

    @Override
    public IItemStack transform(IItemStack stack) {
        //TODO: Always use knownTransformers.get() and skip the function field?
        //  Reason: Multiple calls to transformCustom with the same uid and different functions
        //  would cause de-sync between client/server
        if (function == null) {
            function = knownTransformers.get(uid);
        }

        if(function == null) {
            throw new IllegalStateException("No transformer named '" + uid + "' known!");
        }

        return function.apply(stack);
    }

    @Override
    public String getCommandString(T transformedIngredient) {
        return String.format("%s.transformCustom('%s')", transformedIngredient.getCommandString(), uid);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public IIngredientTransformerSerializer getSerializer() {
        return CraftTweakerRegistries.TRANSFORM_CUSTOM_SERIALIZER;
    }

    public static final class TransformCustomSerializer implements IIngredientTransformerSerializer<TransformCustom<?>> {

        @Override
        public TransformCustom<?> parse(PacketBuffer buffer) {
            return new TransformCustom<>(buffer.readString(), null);
        }

        @Override
        public TransformCustom<?> parse(JsonObject json) {
            final String uid = json.getAsJsonPrimitive("uid").getAsString();
            return new TransformCustom<>(uid, null);
        }

        @Override
        public void write(PacketBuffer buffer, TransformCustom<?> ingredient) {
            buffer.writeString(ingredient.uid);
        }

        @Override
        public JsonObject toJson(TransformCustom<?> transformer) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("uid", transformer.uid);
            return jsonObject;
        }

        @Override
        public ResourceLocation getType() {
            return new ResourceLocation(CraftTweaker.MODID, "transform_custom");
        }
    }
}
