package com.blamejared.crafttweaker.natives.item;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Wearable;

/**
 * Used to denote armor items that can be worn.
 */
@ZenRegister
@Document("vanilla/api/item/Wearable")
@NativeTypeRegistration(value = Wearable.class, zenCodeName = "crafttweaker.api.item.Wearable")
public class ExpandWearable {

}
