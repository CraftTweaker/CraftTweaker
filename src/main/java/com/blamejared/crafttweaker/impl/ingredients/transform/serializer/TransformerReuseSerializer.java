package com.blamejared.crafttweaker.impl.ingredients.transform.serializer;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformReuse;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public enum TransformerReuseSerializer implements IIngredientTransformerSerializer<TransformReuse<?>> {
    INSTANCE;
    
    @Override
    public TransformReuse<?> parse(PacketBuffer buffer) {
        
        return new TransformReuse<>();
    }
    
    @Override
    public TransformReuse<?> parse(JsonObject json) {
        
        return new TransformReuse<>();
    }
    
    @Override
    public void write(PacketBuffer buffer, TransformReuse<?> ingredient) {
        //No-OP
    }
    
    @Override
    public JsonObject toJson(TransformReuse<?> transformer) {
        //No-Op
        return new JsonObject();
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweaker.MODID, "transform_reuse");
    }
    
}