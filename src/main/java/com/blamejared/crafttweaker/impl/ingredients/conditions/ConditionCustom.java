package com.blamejared.crafttweaker.impl.ingredients.conditions;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientCondition;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ConditionCustom<T extends IIngredient> implements IIngredientCondition<T> {
    
    public static final Map<String, Predicate<IItemStack>> knownConditions = new HashMap<>();
    
    private final String uid;
    private Predicate<IItemStack> function;
    
    public ConditionCustom(String uid, Predicate<IItemStack> function) {
        this.uid = uid;
        this.function = function;
        
        if(function != null) {
            knownConditions.put(uid, function);
        }
    }
    
    @Override
    public boolean matches(IItemStack stack) {
        //TODO: Always use knownConditions.get() and skip the function field?
        //  Reason: Multiple calls to conditionCustom with the same uid and different functions
        //  would cause de-sync between client/server
        if(function == null) {
            function = knownConditions.get(uid);
        }
        
        if(function == null) {
            throw new IllegalStateException("No condition named '" + uid + "' known!");
        }
        
        return function.test(stack);
    }
    
    @Override
    public String getCommandString(T ingredient) {
        return String.format("%s.onlyIf('%s')", ingredient.getCommandString(), uid);
    }
    
    @Override
    public boolean ignoresDamage() {
        // Give people a clean slate, mojang really made damage annoying to deal with in 1.14+, so this way if someone wants to ignore the damage they can
        return true;
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    public IIngredientConditionSerializer getSerializer() {
        return CraftTweakerRegistries.CONDITION_CUSTOM_SERIALIZER;
    }
    
    public static final class ConditionCustomSerializer implements IIngredientConditionSerializer<ConditionCustom<?>> {
        
        @Override
        public ConditionCustom<?> parse(PacketBuffer buffer) {
            return new ConditionCustom<>(buffer.readString(), null);
        }
        
        @Override
        public ConditionCustom<?> parse(JsonObject json) {
            final String uid = json.getAsJsonPrimitive("uid").getAsString();
            return new ConditionCustom<>(uid, null);
        }
        
        @Override
        public void write(PacketBuffer buffer, ConditionCustom<?> ingredient) {
            buffer.writeString(ingredient.uid);
        }
        
        @Override
        public JsonObject toJson(ConditionCustom<?> transformer) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("uid", transformer.uid);
            return jsonObject;
        }
        
        @Override
        public ResourceLocation getType() {
            return new ResourceLocation(CraftTweaker.MODID, "condition_custom");
        }
    }
}
