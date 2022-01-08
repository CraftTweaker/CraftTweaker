package com.blamejared.crafttweaker.mixin.common.access.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface AccessItem {
    
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
