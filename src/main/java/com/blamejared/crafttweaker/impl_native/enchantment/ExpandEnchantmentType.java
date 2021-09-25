package com.blamejared.crafttweaker.impl_native.enchantment;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.Item;
import org.openzen.zencode.java.ZenCodeType;

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
