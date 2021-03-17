package com.blamejared.crafttweaker.api.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class IngredientAny extends Ingredient {
    private IngredientAny() {
        super(Stream.of());
    }

    public static final IngredientAny INSTANCE = new IngredientAny();
    private static final ItemStack[] ALL_ITEMS = ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).filter(stack -> !stack.isEmpty()).toArray(ItemStack[]::new);

    @Override
    public ItemStack[] getMatchingStacks() {
        return ALL_ITEMS;
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        return stack != null && !stack.isEmpty();
    }

    @Override
    public JsonElement serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", CraftingHelper.getID(Serializer.INSTANCE).toString());
        return jsonObject;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    public enum Serializer implements IIngredientSerializer<IngredientAny> {
        INSTANCE;

        @Override
        public IngredientAny parse(PacketBuffer buffer) {
            return IngredientAny.INSTANCE;
        }

        @Override
        public IngredientAny parse(JsonObject json) {
            return IngredientAny.INSTANCE;
        }

        @Override
        public void write(PacketBuffer buffer, IngredientAny ingredient) {

        }
    }
}
