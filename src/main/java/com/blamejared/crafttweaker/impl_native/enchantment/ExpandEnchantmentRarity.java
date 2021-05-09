package com.blamejared.crafttweaker.impl_native.enchantment;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.enchantment.Enchantment;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this EnchantmentRarity.COMMON
 */
@ZenRegister
@Document("vanilla/api/enchantment/EnchantmentRarity")
@NativeTypeRegistration(value = Enchantment.Rarity.class, zenCodeName = "crafttweaker.api.enchantment.EnchantmentRarity")
public class ExpandEnchantmentRarity {
    
    /**
     * Gets the weight of this enchantment rarity.
     *
     * @return The weight of the enchantment rarity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("weight")
    public static int getWeight(Enchantment.Rarity internal) {
        
        return internal.getWeight();
    }
    
}
