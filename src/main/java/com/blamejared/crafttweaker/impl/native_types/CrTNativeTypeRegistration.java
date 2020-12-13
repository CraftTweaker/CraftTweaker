package com.blamejared.crafttweaker.impl.native_types;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.common.extensions.IForgeItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Helper class to register Native types
 * TODO: Where should this happen and should it be made available to other
 * mods (e.g. via event?)
 */
public class CrTNativeTypeRegistration {
    
    public static void registerNativeTypes(NativeTypeRegistry registry) {
        registry.addNativeType(Item.class, "crafttweaker.api.item.MCItemDefinition");
        registry.addNativeType(PlayerEntity.class, "crafttweaker.api.player.MCPlayerEntity");
        registry.addNativeType(LivingEntity.class, "crafttweaker.api.entity.MCLivingEntity");
        registry.addNativeType(Entity.class, "crafttweaker.api.entity.MCEntity");
        
        registerType(registry, ItemStack.class);
        registerType(registry, Ingredient.class);
        registerType(registry, CapabilityProvider.class);
        registerType(registry, Material.class);
        
        registerType(registry, AbstractBlock.class);
        registerType(registry, Block.class);
        registerType(registry, BlockState.class);
        registerType(registry, EntityClassification.class);
        registerType(registry, Fluid.class);
        registerType(registry, Effect.class);
        registerType(registry, EffectInstance.class);
        registerType(registry, Potion.class);
        registerType(registry, IItemProvider.class);
        registerType(registry, Biome.class);
        registerType(registry, World.class);
        registerType(registry, ICapabilityProvider.class);
        registerType(registry, IForgeItem.class);
        registerType(registry, IForgeItemStack.class);
        registerType(registry, ForgeRegistryEntry.class);
        registerType(registry, IForgeRegistryEntry.class);
    }
    
    /**
     * Simple way to register a type.
     * Converts the MC name to a CrT one.
     */
    private static void registerType(NativeTypeRegistry registry, Class<?> clazz) {
        final String crtName;
        final String canonicalName = clazz.getCanonicalName();
        if(canonicalName.startsWith("net.minecraftforge")) {
            crtName = "crafttweaker.api" + canonicalName.substring(18);
        } else if(canonicalName.startsWith("net.minecraft")) {
            crtName = "crafttweaker.api" + canonicalName.substring(13);
        } else {
            crtName = "crafttweaker." + clazz.getSimpleName();
        }
        
        registry.addNativeType(clazz, crtName);
    }
}
