package com.blamejared.crafttweaker.natives.item.enchantment;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.enchantment.Enchantment;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/enchantment/Rarity")
@NativeTypeRegistration(value = Enchantment.Rarity.class, zenCodeName = "crafttweaker.api.item.enchantment.Rarity")
@BracketEnum("minecraft:enchantment/rarity")
public class ExpandEnchantmentRarity {
    
    /**
     * Gets the weight of this Rarity
     *
     * @return The weight of this Rarity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("weight")
    public static int getWeight(Enchantment.Rarity internal) {
        
        return internal.getWeight();
    }
    
}
