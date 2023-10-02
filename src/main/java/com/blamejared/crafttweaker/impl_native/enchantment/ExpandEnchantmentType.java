package com.blamejared.crafttweaker.impl_native.enchantment;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @docParam this EnchantmentType.ARMOR
 */
@ZenRegister
@Document("vanilla/api/enchantment/EnchantmentType")
@NativeTypeRegistration(value = EnchantmentType.class, zenCodeName = "crafttweaker.api.enchantment.EnchantmentType")
public class ExpandEnchantmentType {
    
    /**
     * Checks if the given Item can be enchanted with this EnchantmentType
     *
     * @return True if the Item can be enchanted. False otherwise.
     */
    @ZenCodeType.Method
    public static boolean canEnchantItem(EnchantmentType internal, Item itemIn) {
        
        return internal.canEnchantItem(itemIn);
    }
    
    /**
     * Creates a new List of MCEnchantment that define the EnchantmentType as their type.
     *
     * @return A new List of MCEnchantment that define the EnchantmentType as their type.
     */
    @ZenCodeType.Method
    public static List<Enchantment> getEnchantments(EnchantmentType internal) {
        
        final List<Enchantment> enchantments = new ArrayList<>();
        
        for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
            
            if (internal == enchantment.type) {
                
                enchantments.add(enchantment);
            }
        }
        
        return enchantments;
    }
    
    /**
     * Creates a new List of ItemDefinition that are valid for this EnchantmentType.
     *
     * @return A new List of ItemDefinition that are valid for this EnchantmentType.
     */
    @ZenCodeType.Method
    public static List<Item> getItems(EnchantmentType internal) {
        
        final List<Item> items = new ArrayList<>();
        
        for (Item item : ForgeRegistries.ITEMS) {
            
            if (internal.canEnchantItem(item)) {
                
                items.add(item);
            }
        }
        
        return items;
    }
    
    /**
     * Creates a new List of EnchantmentType that are applicable to an item.
     *
     * @param item The item to calculate applicable enchantment types for.
     *
     * @return A new List of EnchantmentType that are applicable to an item.
     *
     * @docParam item <item:minecraft:diamond_sword>
     */
    @ZenCodeType.StaticExpansionMethod
    public static List<EnchantmentType> getTypesForItem(Item item) {
        
        final List<EnchantmentType> validTypes = new ArrayList<>();
        
        for (EnchantmentType type : EnchantmentType.values()) {
            
            if (type.canEnchantItem(item)) {
                
                validTypes.add(type);
            }
        }
        
        return validTypes;
    }
    
    /**
     * Creates a new EnchantmentType with the given name and given can enchantment predicate.
     *
     * @param name                The name of the new EnchantmentType.
     * @param canEnchantPredicate The can enchant predicate.
     *
     * @return A new EnchantmentType with the given name and predicate.
     *
     * @docParam name "wools"
     * @docParam (( item) => item.isIn(<tag:items:minecraft:wool>))
     */
    @ZenCodeType.StaticExpansionMethod
    public static EnchantmentType create(String name, Predicate<Item> canEnchantPredicate) {
        
        return EnchantmentType.create(name, canEnchantPredicate);
    }
    
}
