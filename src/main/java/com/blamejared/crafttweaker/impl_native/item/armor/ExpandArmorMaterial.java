package com.blamejared.crafttweaker.impl_native.item.armor;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.ArmorMaterial;

@ZenRegister
@Document("vanilla/api/item/armor/ArmorMaterial")
@NativeTypeRegistration(value = ArmorMaterial.class, zenCodeName = "crafttweaker.api.item.armor.ArmorMaterial")
public class ExpandArmorMaterial {

}
