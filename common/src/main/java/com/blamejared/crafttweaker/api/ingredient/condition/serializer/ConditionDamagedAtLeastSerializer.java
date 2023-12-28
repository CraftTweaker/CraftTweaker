package com.blamejared.crafttweaker.api.ingredient.condition.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionDamagedAtLeast;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ConditionDamagedAtLeastSerializer implements IIngredientConditionSerializer<ConditionDamagedAtLeast<?>> {
    
    public static final ConditionDamagedAtLeastSerializer INSTANCE = new ConditionDamagedAtLeastSerializer();
    public static final Codec<ConditionDamagedAtLeast<?>> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("damage").forGetter(ConditionDamagedAtLeast::getMinDamage)).
            apply(instance, ConditionDamagedAtLeast::new));
    
    private ConditionDamagedAtLeastSerializer() {}
    
    @Override
    public Codec<ConditionDamagedAtLeast<?>> codec() {
        
        return CODEC;
    }
    
    @Override
    public ConditionDamagedAtLeast<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new ConditionDamagedAtLeast<>(buffer.readVarInt());
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionDamagedAtLeast<?> ingredient) {
        
        buffer.writeVarInt(ingredient.getMinDamage());
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "only_damaged_at_least");
    }
    
}