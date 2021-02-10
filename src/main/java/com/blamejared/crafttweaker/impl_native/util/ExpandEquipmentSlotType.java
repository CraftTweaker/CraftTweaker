package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.inventory.EquipmentSlotType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/MCEquipmentSlotType")
@NativeTypeRegistration(value = EquipmentSlotType.class, zenCodeName = "crafttweaker.api.util.MCEquipmentSlotType")
public class ExpandEquipmentSlotType {
    @ZenCodeType.Getter("name")
    public static String getName(EquipmentSlotType internal) {
        return internal.getName();
    }
    
    @ZenCodeType.Method
    public static boolean isHand(EquipmentSlotType internal) {
        return internal.getSlotType() == EquipmentSlotType.Group.HAND;
    }
    
    @ZenCodeType.Method
    public static boolean isArmor(EquipmentSlotType internal) {
        return internal.getSlotType() == EquipmentSlotType.Group.ARMOR;
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(EquipmentSlotType internal) {
        return "<equipmentSlotType:" + internal.getName() + ">";
    }
}
