package com.blamejared.crafttweaker.impl.ingredients.transform;

import com.blamejared.crafttweaker.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.item.transformed.*;
import com.google.gson.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class TransformReuse<T extends IIngredient> implements IIngredientTransformer<T> {
    
    
    @Override
    public IItemStack transform(IItemStack stack) {
        return stack;
    }
    
    @Override
    public String getCommandString(T transformedIngredient) {
        return String.format("%s.reuse()", transformedIngredient.getCommandString());
    }
    
    @Override
    public TransformerReuseSerializer getSerializer() {
        return CraftTweakerRegistries.TRANSFORM_REUSE_SERIALIZER;
    }
    
    public static final class TransformerReuseSerializer implements IIngredientTransformerSerializer<TransformReuse<?>> {
        
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
}
