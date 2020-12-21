package com.blamejared.crafttweaker.impl.native_types;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * Helper class to register Native types
 * TODO: Where should this happen and should it be made available to other
 * mods (e.g. via event?)
 */
public class CrTNativeTypeRegistration {
    
    public static void registerNativeTypes(NativeTypeRegistry registry) {
        registerNormalTypes(registry);
        registerEventTypes(registry);
    }
    
    private static void registerNormalTypes(NativeTypeRegistry registry) {
        ///registry.addNativeType(Item.class, "crafttweaker.api.item.MCItemDefinition");
        ///registry.addNativeType(PlayerEntity.class, "crafttweaker.api.player.MCPlayerEntity");
        ///registry.addNativeType(LivingEntity.class, "crafttweaker.api.entity.MCLivingEntity");
        ///registry.addNativeType(Entity.class, "crafttweaker.api.entity.MCEntity");
        ///registry.addNativeType(EntityClassification.class, "crafttweaker.api.entity.MCEntityClassification");
        
        ///registry.addNativeType(ItemStack.class, "crafttweaker.api.item.ItemStack");
        ///registry.addNativeType(Ingredient.class, "crafttweaker.api.item.Ingredient");
        
        ///registry.addNativeType(Potion.class, "crafttweaker.api.potion.MCPotion");
        ///registry.addNativeType(Effect.class, "crafttweaker.api.potion.MCPotionEffect");
        ///registry.addNativeType(EffectInstance.class, "crafttweaker.api.potion.MCPotionEffectInstance");
        
        ///registry.addNativeType(Material.class, "crafttweaker.api.block.material.MCMaterial");
        ///registry.addNativeType(MaterialColor.class, "crafttweaker.api.block.material.MCMaterialColor");
        
        ///registry.addNativeType(Block.class, "crafttweaker.api.blocks.MCBlock");
        ///registry.addNativeType(BlockState.class, "crafttweaker.api.blocks.MCBlockState");
        ///registry.addNativeType(Fluid.class, "crafttweaker.api.fluid.MCFluid");
        
        ///registry.addNativeType(Biome.class, "crafttweaker.api.world.MCBiome");
        ///registry.addNativeType(World.class, "crafttweaker.api.world.MCWorld");
        
        ///registry.addNativeType(Enchantment.class, "crafttweaker.api.enchantment.MCEnchantment");
    }
    
    private static void registerEventTypes(NativeTypeRegistry registry) {
        
        ///registry.addNativeType(Event.class, "crafttweaker.api.event.MCEvent");
        ///registry.addNativeType(EntityEvent.class, "crafttweaker.api.event.entity.MCEntityEvent");
        ///registry.addNativeType(LivingEvent.class, "crafttweaker.api.event.entity.MCLivingEvent");
        ///registry.addNativeType(PlayerEvent.class, "crafttweaker.api.event.entity.player.MCPlayerEvent");
        ///registry.addNativeType(AnvilRepairEvent.class, "crafttweaker.api.event.entity.player.MCAnvilRepairEvent");
    }
    
}
