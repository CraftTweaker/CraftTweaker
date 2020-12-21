package com.blamejared.crafttweaker.impl_native.enchantment;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.enchantment.Enchantment;

@ZenRegister
@NativeTypeRegistration(value = Enchantment.class, zenCodeName = "crafttweaker.api.enchantment.MCEnchantment")
public class ExpandEnchantment {}
