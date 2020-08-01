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

public class ConditionAnyDamage<T extends IIngredient> implements IIngredientCondition<T> {
    
    @Override
    public boolean matches(IItemStack stack) {
        return true;
    }
    
    @Override
    public boolean ignoresDamage() {
        return true;
    }
    
    @Override
    public String getCommandString(IIngredient ingredient) {
        return ingredient.getCommandString() + ".anyDamage()";
    }
    
    @Override
    public IIngredientConditionSerializer getSerializer() {
        return CraftTweakerRegistries.CONDITION_ANY_DAMAGE_SERIALIZER;
    }
    
    public static final class ConditionAnyDamagedSerializer implements IIngredientConditionSerializer<ConditionAnyDamage<?>> {
        
        @Override
        public ConditionAnyDamage<?> parse(PacketBuffer buffer) {
            return new ConditionAnyDamage<>();
        }
        
        @Override
        public ConditionAnyDamage<?> parse(JsonObject json) {
            return new ConditionAnyDamage<>();
        }
        
        @Override
        public void write(PacketBuffer buffer, ConditionAnyDamage<?> ingredient) {
        }
        
        @Override
        public JsonObject toJson(ConditionAnyDamage<?> transformer) {
            return new JsonObject();
        }
        
        @Override
        public ResourceLocation getType() {
            return new ResourceLocation(CraftTweaker.MODID, "any_damage");
        }
    }
}
