package com.blamejared.crafttweaker.impl.native_types;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.capabilities.CapabilityProvider;

/**
 * Helper class to register Native types
 * TODO: Where should this happen and should it be made available to other
 *   mods (e.g. via event?)
 */
public class CrTNativeTypeRegistration {
    
    public static void registerNativeTypes(NativeTypeRegistry registry) {
        registerType(registry, ItemStack.class);
        registerType(registry, Item.class);
        registerType(registry, Ingredient.class);
        registerType(registry, PlayerEntity.class);
        registerType(registry, LivingEntity.class);
        registerType(registry, Entity.class);
        registerType(registry, CapabilityProvider.class);
        registerType(registry, Material.class);
    }
    
    /**
     * Registers a native type as "crafttweaker.<SimpleClassName>"
     * TODO: Remove me?
     */
    private static void registerType(NativeTypeRegistry registry, Class<?> clazz) {
        registry.addNativeType(clazz, "crafttweaker." + clazz.getSimpleName());
    }
}
