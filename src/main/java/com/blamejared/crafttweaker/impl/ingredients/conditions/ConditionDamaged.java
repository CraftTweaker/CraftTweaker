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

public class ConditionDamaged<T extends IIngredient> implements IIngredientCondition<T> {
    
    @Override
    public boolean matches(IItemStack stack) {
        return stack.getDamage() > 0;
    }
    
    @Override
    public boolean ignoresDamage() {
        return true;
    }
    
    @Override
    public String getCommandString(IIngredient ingredient) {
        return ingredient.getCommandString() + ".onlyDamaged()";
    }
    
    @Override
    public IIngredientConditionSerializer getSerializer() {
        return CraftTweakerRegistries.CONDITION_DAMAGE_SERIALIZER;
    }
    
    public static final class ConditionDamagedSerializer implements IIngredientConditionSerializer<ConditionDamaged<?>> {
        
        @Override
        public ConditionDamaged<?> parse(PacketBuffer buffer) {
            return new ConditionDamaged<>();
        }
        
        @Override
        public ConditionDamaged<?> parse(JsonObject json) {
            return new ConditionDamaged<>();
        }
        
        @Override
        public void write(PacketBuffer buffer, ConditionDamaged<?> ingredient) {
        }
        
        @Override
        public JsonObject toJson(ConditionDamaged<?> transformer) {
            return new JsonObject();
        }
        
        @Override
        public ResourceLocation getType() {
            return new ResourceLocation(CraftTweaker.MODID, "only_damaged");
        }
    }
}
