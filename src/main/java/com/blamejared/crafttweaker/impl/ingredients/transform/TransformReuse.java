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

public class TransformReuse<T extends IIngredient> implements IIngredientTransformer<T> {
    
    
    @Override
    public IItemStack transform(IItemStack stack) {
        
        return stack.copy();
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
