package com.blamejared.crafttweaker.impl.ingredients.transform;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.transformed.IngredientTransformed;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class TransformReplace<T extends IIngredient> implements IIngredientTransformer<T> {
    private final IItemStack replaceWith;

    public TransformReplace(IItemStack replaceWith) {
        this.replaceWith = replaceWith;
    }

    @Override
    public IItemStack transform(IItemStack stack) {
        return new MCItemStack(replaceWith.getInternal().copy());
    }

    @Override
    public String getCommandString(T transformedIngredient) {
        return String.format("%s.transformReplace(%s)", transformedIngredient.getCommandString(), replaceWith.getCommandString());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public IIngredientTransformerSerializer getSerializer() {
        return CraftTweakerRegistries.TRANSFORM_REPLACE_SERIALIZER;
    }

    public static final class TransformReplaceTransformerSerializer implements IIngredientTransformerSerializer<TransformReplace<?>> {

        @Override
        public TransformReplace<?> parse(PacketBuffer buffer) {
            final ItemStack replaceWith = buffer.readItemStack();
            return new TransformReplace<>(new MCItemStack(replaceWith));
        }

        @Override
        public TransformReplace<?> parse(JsonObject json) {
    
            final Ingredient.IItemList iItemList = Ingredient.deserializeItemList(json.getAsJsonObject("replaceWith"));
            final ItemStack replaceWith = iItemList.getStacks().iterator().next();
            return new TransformReplace<>(new MCItemStack(replaceWith));
        }

        @Override
        public void write(PacketBuffer buffer, TransformReplace<?> ingredient) {
            buffer.writeItemStack(ingredient.replaceWith.getInternal());
        }

        @Override
        public JsonObject toJson(TransformReplace<?> transformer) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", getType().toString());
            jsonObject.add("replaceWith", transformer.replaceWith.asVanillaIngredient().serialize());
            return jsonObject;
        }

        @Override
        public ResourceLocation getType() {
            return new ResourceLocation(CraftTweaker.MODID, "transform_replace");
        }
    }
}
