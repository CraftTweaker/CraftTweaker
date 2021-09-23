package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Hand;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/MCHand")
@NativeTypeRegistration(value = Hand.class, zenCodeName = "crafttweaker.api.util.MCHand")
public class ExpandHand {
    
    @ZenCodeType.Caster(implicit = true)
    public static String getName(Hand internal) {
        
        return internal.name();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static EquipmentSlotType asEquipmentSlotType(Hand internal) {
        
        switch(internal) {
            case MAIN_HAND:
                return EquipmentSlotType.MAINHAND;
            case OFF_HAND:
                return EquipmentSlotType.OFFHAND;
            default:
                throw new IllegalArgumentException("Invalid hand: " + internal.name());
        }
    }
    
}
