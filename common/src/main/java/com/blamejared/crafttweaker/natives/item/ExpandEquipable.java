package com.blamejared.crafttweaker.natives.item;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Equipable;

/**
 * Used to denote armor items that can be worn.
 */
@ZenRegister
@Document("vanilla/api/item/Equipable")
@NativeTypeRegistration(value = Equipable.class, zenCodeName = "crafttweaker.api.item.Equipable")
public class ExpandEquipable {

}
