package com.blamejared.crafttweaker.mixin.common.access.food;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(FoodProperties.class)
public interface AccessFoodProperties {
    
    @Invoker("<init>")
    static FoodProperties crafttweaker$createFoodProperties(int nutrition, float saturationModifier, boolean isMeat, boolean canAlwaysEat, boolean fastFood, List<Pair<MobEffectInstance, Float>> effects) {
        throw new UnsupportedOperationException();
    }
    
    @Mutable
    @Accessor("nutrition")
    void crafttweaker$setNutrition(int nutrition);
    
    @Mutable
    @Accessor("saturationModifier")
    void crafttweaker$setSaturationModifier(float saturationModifier);
    
    @Mutable
    @Accessor("isMeat")
    void crafttweaker$setIsMeat(boolean isMeat);
    
    @Mutable
    @Accessor("canAlwaysEat")
    void crafttweaker$setCanAlwaysEat(boolean canAlwaysEat);
    
    @Mutable
    @Accessor("fastFood")
    void crafttweaker$setFastFood(boolean fastFood);
    
    @Accessor("effects")
    List<Pair<MobEffectInstance, Float>> crafttweaker$getEffects();
    
    @Mutable
    @Accessor("effects")
    void crafttweaker$setEffects(List<Pair<MobEffectInstance, Float>> effects);
    
}
