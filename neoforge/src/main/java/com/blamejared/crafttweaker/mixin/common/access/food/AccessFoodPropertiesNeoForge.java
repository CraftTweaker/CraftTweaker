package com.blamejared.crafttweaker.mixin.common.access.food;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.function.Supplier;

@Mixin(FoodProperties.class)
public interface AccessFoodPropertiesNeoForge {
    
    @Accessor("effects")
    List<Pair<Supplier<MobEffectInstance>, Float>> crafttweaker$getEffects();
    
}
