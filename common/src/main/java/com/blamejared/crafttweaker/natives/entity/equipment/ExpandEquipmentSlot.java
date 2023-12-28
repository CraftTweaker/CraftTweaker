package com.blamejared.crafttweaker.natives.entity.equipment;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.EquipmentSlot;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/equipment/EquipmentSlot")
@NativeTypeRegistration(value = EquipmentSlot.class, zenCodeName = "crafttweaker.api.entity.equipment.EquipmentSlot")
@BracketEnum("minecraft:equipmentslot")
public class ExpandEquipmentSlot {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("type")
    public static EquipmentSlot.Type getType(EquipmentSlot internal) {
        
        return internal.getType();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("index")
    public static int getIndex(EquipmentSlot internal) {
        
        return internal.getIndex();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(EquipmentSlot internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(EquipmentSlot internal) {
        
        return "<constant:minecraft:equipmentslot:" + internal.getName() + ">";
    }
    
}
