package com.blamejared.crafttweaker.mixin.common.access.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(Item.class)
public interface AccessItem {
    
    @Accessor("BASE_ATTACK_DAMAGE_UUID")
    static UUID crafttweaker$getBASE_ATTACK_DAMAGE_UUID() {throw new UnsupportedOperationException();}
    
    @Accessor("BASE_ATTACK_SPEED_UUID")
    static UUID crafttweaker$getBASE_ATTACK_SPEED_UUID() {throw new UnsupportedOperationException();}
    
    @Mutable
    @Accessor("maxStackSize")
    void crafttweaker$setMaxStackSize(int newSize);
    
    @Mutable
    @Accessor("rarity")
    void crafttweaker$setRarity(Rarity newRarity);
    
    @Mutable
    @Accessor("maxDamage")
    void crafttweaker$setMaxDamage(int newMaxDamage);
    
    @Mutable
    @Accessor("isFireResistant")
    void crafttweaker$setFireResistant(boolean isFireResistant);
    
    @Mutable
    @Accessor("foodProperties")
    void crafttweaker$setFoodProperties(FoodProperties newFood);
    
    
}
