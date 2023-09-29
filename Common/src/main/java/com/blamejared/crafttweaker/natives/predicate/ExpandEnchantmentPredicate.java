package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.item.enchantment.Enchantment;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/EnchantmentPredicate")
@NativeTypeRegistration(value = EnchantmentPredicate.class, zenCodeName = "crafttweaker.api.predicate.EnchantmentPredicate")
public final class ExpandEnchantmentPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static EnchantmentPredicate create(final Enchantment enchantment) {
        
        return create(enchantment, MinMaxBounds.Ints.ANY);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EnchantmentPredicate create(final MinMaxBounds.Ints level) {
        
        return create(null, level);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EnchantmentPredicate create(final Enchantment enchantment, final MinMaxBounds.Ints level) {
        
        return new EnchantmentPredicate(enchantment, level);
    }
    
}
