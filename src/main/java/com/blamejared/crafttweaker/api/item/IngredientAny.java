package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientAnySerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.Stream;

//TODO - BREAKING (potentially): Move this to com.blamejared.crafttweaker.api.ingredient
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class IngredientAny extends Ingredient {
    
    public static final IngredientAny INSTANCE = new IngredientAny();
    private static final Lazy<ItemStack[]> ALL_ITEMS = Lazy.concurrentOf(() -> ForgeRegistries.ITEMS.getValues()
            .stream()
            .map(Item::getDefaultInstance)
            .filter(stack -> !stack.isEmpty())
            .toArray(ItemStack[]::new));
    
    private IngredientAny() {
        
        super(Stream.empty());
    }
    
    @Override
    public ItemStack[] getMatchingStacks() {
        
        return ALL_ITEMS.get();
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        return stack != null && !stack.isEmpty();
    }
    
    @Override
    public JsonElement serialize() {
        
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", CraftingHelper.getID(IngredientAnySerializer.INSTANCE).toString());
        return jsonObject;
    }
    
    @Override
    public boolean isSimple() {
        
        return false;
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return IngredientAnySerializer.INSTANCE;
    }
    
    
}
