package com.blamejared.crafttweaker.natives.item.type.armor;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.ArmorMaterials;

@ZenRegister
@Document("vanilla/api/item/type/armor/ArmorMaterials")
@NativeTypeRegistration(value = ArmorMaterials.class, zenCodeName = "crafttweaker.api.item.type.armor.ArmorMaterials")
@BracketEnum("minecraft:armor/materials")
public class ExpandArmorMaterials {

}
