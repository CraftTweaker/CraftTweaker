package com.blamejared.crafttweaker.impl_native.enchantment.vanishing;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.enchantment.IArmorVanishable;

/**
 * Used to denote armor items that can have the vanishing enchantment applied.
 */
@ZenRegister
@Document("vanilla/api/enchantment/IArmorVanishable")
@NativeTypeRegistration(value = IArmorVanishable.class, zenCodeName = "crafttweaker.api.enchantment.IArmorVanishable")
public class ExpandIArmorVanishable {

}
