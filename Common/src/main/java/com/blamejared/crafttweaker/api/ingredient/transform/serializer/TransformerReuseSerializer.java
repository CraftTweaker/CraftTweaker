package com.blamejared.crafttweaker.api.ingredient.transform.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformReuse;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public enum TransformerReuseSerializer implements IIngredientTransformerSerializer<TransformReuse<?>> {
    INSTANCE;
    
    @Override
    public TransformReuse<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new TransformReuse<>();
    }
    
    @Override
    public TransformReuse<?> fromJson(JsonObject json) {
        
        return new TransformReuse<>();
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, TransformReuse<?> ingredient) {
        //No-OP
    }
    
    @Override
    public JsonObject toJson(TransformReuse<?> transformer) {
        //No-Op
        return new JsonObject();
    }
    
    @Override
    public ResourceLocation getType() {
        
        return CraftTweakerConstants.rl("transform_reuse");
    }
    
}