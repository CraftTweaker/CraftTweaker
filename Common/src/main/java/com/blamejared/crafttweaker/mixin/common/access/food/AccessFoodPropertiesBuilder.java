package com.blamejared.crafttweaker.mixin.common.access.food;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(FoodProperties.Builder.class)
public interface AccessFoodPropertiesBuilder {
    
    @Accessor("isMeat")
    void crafttweaker$setIsMeat(boolean isMeat);
    
    @Accessor("canAlwaysEat")
    void crafttweaker$setCanAlwaysEat(boolean canAlwaysEat);
    
    @Accessor("fastFood")
    void crafttweaker$setFastFood(boolean fastFood);
    
    @Accessor("effects")
    List<Pair<MobEffectInstance, Float>> crafttweaker$getEffects();
    
}
