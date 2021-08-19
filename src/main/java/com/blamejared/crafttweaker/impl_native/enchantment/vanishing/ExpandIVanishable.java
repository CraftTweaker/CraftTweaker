package com.blamejared.crafttweaker.impl_native.enchantment.vanishing;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.enchantment.IVanishable;

/**
 * Used to denote items and blocks that can have the vanishing enchantment applied.
 */
@ZenRegister
@Document("vanilla/api/enchantment/vanishing/IVanishable")
@NativeTypeRegistration(value = IVanishable.class, zenCodeName = "crafttweaker.api.enchantment.vanishing.IVanishable")
public class ExpandIVanishable {

}
