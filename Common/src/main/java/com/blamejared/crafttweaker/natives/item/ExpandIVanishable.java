package com.blamejared.crafttweaker.natives.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Vanishable;

/**
 * Used to denote items and blocks that can have the vanishing enchantment applied.
 */
@ZenRegister
@Document("vanilla/api/item/Vanishable")
@NativeTypeRegistration(value = Vanishable.class, zenCodeName = "crafttweaker.api.item.Vanishable")
public class ExpandIVanishable {

}
