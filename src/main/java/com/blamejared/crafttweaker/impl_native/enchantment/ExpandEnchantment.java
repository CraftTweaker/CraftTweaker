package com.blamejared.crafttweaker.impl_native.enchantment;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @docParam this <enchantment:minecraft:protection>
 */
@ZenRegister
@Document("vanilla/api/enchantment/MCEnchantment")
@NativeTypeRegistration(value = Enchantment.class, zenCodeName = "crafttweaker.api.enchantment.MCEnchantment")
public class ExpandEnchantment {
    
    /**
     * Gets the Rarity of this Enchantment
     *
     * @return The Rarity of this Enchantment
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("rarity")
    public static Enchantment.Rarity getRarity(Enchantment internal) {
        
        return internal.getRarity();
    }
    
    /**
     * Gets a map of all equipment that the entity is currently holding that this Enchantment can be applied to.
     *
     * @param livingEntity The entity to check.
     *
     * @return A map of EquipmentSlotType to IItemStack
     *
     * @docParam livingEntity entity
     */
    @ZenCodeType.Method
    public static Map<EquipmentSlotType, IItemStack> getEntityEquipment(Enchantment internal, LivingEntity livingEntity) {
        
        return internal.getEntityEquipment(livingEntity)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, o -> new MCItemStack(o.getValue())));
    }
    
    /**
     * Gets the minimum level for this Enchantment.
     *
     * @return The minimum level for this Enchantment.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("minLevel")
    public static int getMinLevel(Enchantment internal) {
        
        return internal.getMinLevel();
    }
    
    /**
     * Gets the maximum level for this Enchantment.
     *
     * @return The maximum level for this Enchantment.
     */
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxLevel")
    public static int getMaxLevel(Enchantment internal) {
        
        return internal.getMaxLevel();
    }
    
    /**
     * Gets the minimum enchantability required to enchant this Enchantment at the given enchantment level.
     *
     * For example, to enchant an item with Fire Aspect at level 1, you need an enchantability of at-least 10.
     *
     * @param enchantmentLevel The enchantment level to get the enchantability for.
     *
     * @return The enchantability of the Enchantment at the given level.
     *
     * @docParam enchantmentLevel 1
     */
    @ZenCodeType.Method
    public static int getMinEnchantability(Enchantment internal, int enchantmentLevel) {
        
        return internal.getMinEnchantability(enchantmentLevel);
    }
    
    /**
     * Gets the maximum enchantability required to enchant this Enchantment at the given enchantment level.
     *
     * For example, to enchant an item with Fire Aspect at level 1, you need an enchantability that is less than 60..
     *
     * @param enchantmentLevel The enchantment level to get the enchantability for.
     *
     * @return The enchantability of the Enchantment at the given level.
     *
     * @docParam enchantmentLevel 1
     */
    @ZenCodeType.Method
    public static int getMaxEnchantability(Enchantment internal, int enchantmentLevel) {
        
        return internal.getMaxEnchantability(enchantmentLevel);
    }
    
    /**
     * Checks if this Enchantment is compatible with the other given Enchantment.
     *
     * @param other The other Enchantment to check if it is compatible with this Enchantment
     *
     * @return True if the Enchantments are compatible. False otherwise.
     *
     * @docParam other <enchantment:minecraft:efficiency>
     */
    @ZenCodeType.Method
    public static boolean isCompatibleWith(Enchantment internal, Enchantment other) {
        
        return internal.isCompatibleWith(other);
    }
    
    /**
     * Gets the name of this Enchantment
     *
     * @return The name of this Enchantment
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(Enchantment internal) {
        
        return internal.getName();
    }
    
    /**
     * Gets the display name of this Enchantment at the given level.
     *
     * @param level The level of this enchantment
     *
     * @return The display name of this Enchantment at the level.
     *
     * @docParam level 2
     */
    @ZenCodeType.Method
    public static MCTextComponent getDisplayName(Enchantment internal, int level) {
        
        return new MCTextComponent(internal.getDisplayName(level));
    }
    
    /**
     * Checks if this Enchantment can be applied to the given IItemStack.
     *
     * @param stack The stack to check if this enchantment can be applied to.
     *
     * @return True if this Enchantment can apply to the given IItemStack. False otherwise.
     *
     * @docParam stack <item:minecraft:diamond_sword>
     */
    @ZenCodeType.Method
    public static boolean canApply(Enchantment internal, IItemStack stack) {
        
        return internal.canApply(stack.getInternal());
    }
    
    /**
     * Checks if this Enchantment is a treasure Enchantment.
     *
     * @return True if this Enchantment is a treasure enchantment. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("treasure")
    public static boolean isTreasureEnchantment(Enchantment internal) {
        
        return internal.isTreasureEnchantment();
    }
    
    /**
     * Checks if this Enchantment is a curse Enchantment.
     *
     * @return True if this Enchantment is a curse enchantment. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("curse")
    public static boolean isCurse(Enchantment internal) {
        
        return internal.isCurse();
    }
    
    /**
     * Checks if a villager can sell this Enchantment.
     *
     * @return True if a villager can sell this Enchantment. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("canVillagerTrade")
    public static boolean canVillagerTrade(Enchantment internal) {
        
        return internal.canVillagerTrade();
    }
    
    /**
     * Checks if this Enchantment can generate in loot.
     *
     * @return True if this Enchantment can generate in loot.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("canGenerateInLoot")
    public static boolean canGenerateInLoot(Enchantment internal) {
        
        return internal.canGenerateInLoot();
    }
    
    /**
     * Checks if this Enchantment can be applied to the given IItemStack at an enchanting table..
     *
     * @param stack The IItemStack to check against.
     *
     * @return True if this Enchantment can be applied. False otherwise.
     *
     * @docParam stack <item:minecraft:stone_sword>
     */
    @ZenCodeType.Method
    public static boolean canApplyAtEnchantingTable(Enchantment internal, ItemStack stack) {
        
        return internal.canApplyAtEnchantingTable(stack);
    }
    
    /**
     * Check if this Enchantment is allowed on books.
     *
     * @return True if this Enchantment can be applied on books. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("allowedOnBook")
    public static boolean isAllowedOnBooks(Enchantment internal) {
        
        return internal.isAllowedOnBooks();
    }
    
}
