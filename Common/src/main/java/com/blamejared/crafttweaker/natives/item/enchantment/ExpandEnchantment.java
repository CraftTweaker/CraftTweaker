package com.blamejared.crafttweaker.natives.item.enchantment;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;

@ZenRegister
@Document("vanilla/api/item/enchantment/Enchantment")
@NativeTypeRegistration(value = Enchantment.class, zenCodeName = "crafttweaker.api.item.enchantment.Enchantment")
@TaggableElement("minecraft:enchantment")
public class ExpandEnchantment {
    
    @ZenCodeType.Method
    public static Map<EquipmentSlot, ItemStack> getSlotItems(Enchantment internal, LivingEntity entity) {
        
        return internal.getSlotItems(entity);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("rarity")
    public static Enchantment.Rarity getRarity(Enchantment internal) {
        
        return internal.getRarity();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("minLevel")
    public static int getMinLevel(Enchantment internal) {
        
        return internal.getMinLevel();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxLevel")
    public static int getMaxLevel(Enchantment internal) {
        
        return internal.getMaxLevel();
    }
    
    @ZenCodeType.Method
    public static int getMinCost(Enchantment internal, int level) {
        
        return internal.getMinCost(level);
    }
    
    @ZenCodeType.Method
    public static int getMaxCost(Enchantment internal, int level) {
        
        return internal.getMaxCost(level);
    }
    
    @ZenCodeType.Method
    public static int getDamageProtection(Enchantment internal, int level, DamageSource source) {
        
        return internal.getDamageProtection(level, source);
    }
    
    @ZenCodeType.Method
    public static float getDamageBonus(Enchantment internal, int level, MobType mobType) {
        
        return internal.getDamageBonus(level, mobType);
    }
    
    @ZenCodeType.Method
    public static boolean isCompatibleWith(Enchantment internal, Enchantment other) {
        
        return internal.isCompatibleWith(other);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("descriptionId")
    public static String getDescriptionId(Enchantment internal) {
        
        return internal.getDescriptionId();
    }
    
    @ZenCodeType.Method
    public static Component getFullname(Enchantment internal, int level) {
        
        return internal.getFullname(level);
    }
    
    @ZenCodeType.Method
    public static boolean canEnchant(Enchantment internal, ItemStack stack) {
        
        return internal.canEnchant(stack);
    }
    
    @ZenCodeType.Method
    public static void doPostAttack(Enchantment internal, LivingEntity source, Entity target, int level) {
        
        internal.doPostAttack(source, target, level);
    }
    
    @ZenCodeType.Method
    public static void doPostHurt(Enchantment internal, LivingEntity source, Entity target, int level) {
        
        internal.doPostHurt(source, target, level);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isTreasureOnly")
    public static boolean isTreasureOnly(Enchantment internal) {
        
        return internal.isTreasureOnly();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isCurse")
    public static boolean isCurse(Enchantment internal) {
        
        return internal.isCurse();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isTradeable")
    public static boolean isTradeable(Enchantment internal) {
        
        return internal.isTradeable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isDiscoverable")
    public static boolean isDiscoverable(Enchantment internal) {
        
        return internal.isDiscoverable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(Enchantment internal) {
        
        return Registry.ENCHANTMENT.getKey(internal);
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Enchantment internal) {
        
        return "<enchantment:" + Registry.ENCHANTMENT.getKey(internal) + ">";
    }
    
}
