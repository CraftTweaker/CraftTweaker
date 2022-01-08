package com.blamejared.crafttweaker.natives.entity.equipment;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.EquipmentSlot;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Locale;

@ZenRegister
@Document("vanilla/api/entity/equipment/EquipmentSlotType")
@NativeTypeRegistration(value = EquipmentSlot.Type.class, zenCodeName = "crafttweaker.api.entity.equipment.EquipmentSlotType")
@BracketEnum("minecraft:equipmentslot/type")
public class ExpandEquipmentSlotType {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(EquipmentSlot.Type internal) {
        
        return "<constant:minecraft:equipmentslot/type:" + internal.name()
                .toLowerCase(Locale.ROOT) + ">";
    }
    
}
