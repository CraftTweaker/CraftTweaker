package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientAnySerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
public class IngredientAny extends Ingredient implements IngredientSingleton<IIngredientAny> {
    
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
    public ItemStack[] getItems() {
        
        return ALL_ITEMS.get();
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        return stack != null && !stack.isEmpty();
    }
    
    @Override
    public JsonElement toJson() {
        
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
    
    
    @Override
    public IIngredientAny getInstance() {
        
        return IIngredientAny.INSTANCE;
    }
    
}
