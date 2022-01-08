package com.blamejared.crafttweaker.api.ingredient.transform.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformReplace;
import com.blamejared.crafttweaker.platform.Services;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public enum TransformReplaceSerializer implements IIngredientTransformerSerializer<TransformReplace<?>> {
    INSTANCE;
    
    @Override
    public TransformReplace<?> fromNetwork(FriendlyByteBuf buffer) {
        
        final ItemStack replaceWith = buffer.readItem();
        return new TransformReplace<>(Services.PLATFORM.createMCItemStack(replaceWith));
    }
    
    @Override
    public TransformReplace<?> fromJson(JsonObject json) {
        
        final Ingredient.Value iItemList = Ingredient.valueFromJson(json.getAsJsonObject("replaceWith"));
        final ItemStack replaceWith = iItemList.getItems().iterator().next();
        return new TransformReplace<>(Services.PLATFORM.createMCItemStack(replaceWith));
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, TransformReplace<?> ingredient) {
        
        buffer.writeItem(ingredient.getReplaceWith().getInternal());
    }
    
    @Override
    public JsonObject toJson(TransformReplace<?> transformer) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", getType().toString());
        jsonObject.add("replaceWith", transformer.getReplaceWith().asVanillaIngredient().toJson());
        return jsonObject;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return CraftTweakerConstants.rl("transform_replace");
    }
    
}