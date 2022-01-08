package com.blamejared.crafttweaker.natives.item.enchantment;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/enchantment/EnchantmentCategory")
@NativeTypeRegistration(value = EnchantmentCategory.class, zenCodeName = "crafttweaker.api.item.enchantment.EnchantmentCategory")
@BracketEnum("minecraft:enchantment/category")
public class ExpandEnchantmentCategory {
    
    @ZenCodeType.Method
    public static boolean canEnchant(EnchantmentCategory internal, Item item) {
        
        return internal.canEnchant(item);
    }
    
}
