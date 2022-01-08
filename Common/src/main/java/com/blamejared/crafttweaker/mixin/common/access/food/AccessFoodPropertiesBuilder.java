package com.blamejared.crafttweaker.mixin.common.access.food;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(FoodProperties.Builder.class)
public interface AccessFoodPropertiesBuilder {
    
    @Accessor
    void setIsMeat(boolean isMeat);
    
    @Accessor
    void setCanAlwaysEat(boolean canAlwaysEat);
    
    @Accessor
    void setFastFood(boolean fastFood);
    
    @Accessor
    List<Pair<MobEffectInstance, Float>> getEffects();
    
}
