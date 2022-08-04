package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientConditioned;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.NBTIngredient;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("rawtypes")
@ParametersAreNonnullByDefault
public enum IngredientConditionedSerializer implements IIngredientSerializer<IngredientConditioned<?, ?>> {
    INSTANCE;
    
    public JsonObject toJson(IngredientConditioned<?, ?> ingredientVanillaPlus) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", CraftingHelper.getID(IngredientConditionedSerializer.INSTANCE).toString());
        jsonObject.add("base", ingredientVanillaPlus.getCrTIngredient()
                .getBaseIngredient()
                .asVanillaIngredient()
                .toJson());
        final IIngredientCondition<?> condition = ingredientVanillaPlus.getCondition();
        final JsonObject value = condition.toJson();
        if(!value.has("type")) {
            value.addProperty("type", condition.getType().toString());
        }
        jsonObject.add("condition", value);
        
        return jsonObject;
    }
    
    @Override
    public IngredientConditioned<?, ?> parse(FriendlyByteBuf buffer) {
        
        final IIngredient base = IIngredient.fromIngredient(Ingredient.fromNetwork(buffer));
        final ResourceLocation type = buffer.readResourceLocation();
        final IIngredientConditionSerializer<?> value = CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        return new IngredientConditioned(new IIngredientConditioned(base, value.fromNetwork(buffer)));
    }
    
    @Override
    public IngredientConditioned<?, ?> parse(JsonObject json) {
        
        final JsonObject base = json.getAsJsonObject("base");
        final IIngredient baseIngredient = IIngredient.fromIngredient(CraftingHelper.getIngredient(base));
        
        final JsonObject condition = json.getAsJsonObject("condition");
        final ResourceLocation type = new ResourceLocation(condition.get("type").getAsString());
        final IIngredientConditionSerializer<?> value = CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        return new IngredientConditioned(new IIngredientConditioned(baseIngredient, value.fromJson(condition)));
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
