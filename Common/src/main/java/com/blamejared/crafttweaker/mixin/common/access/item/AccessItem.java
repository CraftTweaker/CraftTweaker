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
    static UUID getBASE_ATTACK_DAMAGE_UUID() {throw new UnsupportedOperationException();}
    
    @Accessor("BASE_ATTACK_SPEED_UUID")
    static UUID getBASE_ATTACK_SPEED_UUID() {throw new UnsupportedOperationException();}
    
    @Mutable
    @Accessor("maxStackSize")
    void setMaxStackSize(int newSize);
    
    @Mutable
    @Accessor("rarity")
    void setRarity(Rarity newRarity);
    
    @Mutable
    @Accessor("maxDamage")
    void setMaxDamage(int newMaxDamage);
    
    @Mutable
    @Accessor("isFireResistant")
    void setFireResistant(boolean isFireResistant);
    
    @Mutable
    @Accessor("foodProperties")
    void setFoodProperties(FoodProperties newFood);
    
    
}
